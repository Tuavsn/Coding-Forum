'use client'
import { CheckCircleOutlined, ClockCircleOutlined, CloudServerOutlined, HistoryOutlined, PlayCircleOutlined } from "@ant-design/icons";
import { Button, Card, Divider, Flex, Modal, Skeleton, Tag, Typography } from "antd";
import LanguageMenu from "./LanguageMenu";
import MonacoEditor from "./MonacoEditor";
import { useContext, useState } from "react";
import { useSearchParams } from "next/navigation";
import { AuthContext } from "@/context/AuthContextProvider";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { getProblemDetail, runSolution, submitSolution } from "@/libs/actions/problem.actions";
import { Problem, ProblemSubmission, SubmissionResult } from "@/libs/types";
import { ProblemSubmissionLanguageType, ProblemType } from "@/libs/enum";

function getTopicColor(str: string): string {
    switch(str) {
        case ProblemType.EASY:
            return "#87d068"
        case ProblemType.MEDIUM:
            return "#108ee9"
        default:
            return "#f50"
    }
}

export default function ProblemDetail({problem}: {problem: Problem}) {
    const queryClient = useQueryClient();

    const problemId = useSearchParams().get('id');

    const { data, isLoading } = useQuery<Problem>('getProblemDetail', () => getProblemDetail(problemId));

    const [runLoading, setRunLoading] = useState(false);

    const [submitLoading, setSubmitLoading] = useState(false);

    const [code, setCode] = useState('');

    const [language, setLanguage] = useState(ProblemSubmissionLanguageType.CPLUSPLUS);

    const [runResult, setRunResult] = useState<SubmissionResult>();
    
    const [submitResult, setSubmitResult] = useState<ProblemSubmission>();

    const [isModalVisible, setIsModalVisible] = useState(false);

    // run solution
    const runSolutionMutation = useMutation(runSolution, {
        onMutate: () => {
            setRunLoading(true);
        },

        onSuccess: (data) => {
            setRunResult(data);
            setRunLoading(false);
        }
    })

    const handleRunSolution = () => {
        runSolutionMutation.mutate({
            problemId: problemId as string,
            solution: {
                languageType: language,
                code: code
            }
        });
    }

    // submit solution
    const submitSolutionMutation = useMutation(submitSolution, {
        onMutate: () => {
            setSubmitLoading(true);
        },

        onSuccess: (data) => {
            setSubmitResult(data);
            setIsModalVisible(true);
            setSubmitLoading(false);
        }
    })

    const handleSubmitSolution = () => {
        submitSolutionMutation.mutate({
            problemId: problemId as string,
            solution: {
                languageType: language,
                code: code
            }
        })
    }

    const handleOk = () => {
        setIsModalVisible(false);
    };
    
    const handleCancel = () => {
        setIsModalVisible(false);
    };
    
    if(isLoading) {
        return <Skeleton active />
    }
    return (
        data && (
            <>
                <Flex gap={6} justify="center" className="m-4">
                    <Button size="large" type="primary" loading={runLoading} disabled={runLoading || submitLoading} onClick={handleRunSolution}><PlayCircleOutlined /><p>Run</p></Button>
                    <Button size="large" type="primary" loading={submitLoading} disabled={runLoading || submitLoading} onClick={handleSubmitSolution}><CloudServerOutlined /><p>Submit</p></Button>
                    <LanguageMenu />
                    <Button size="large" type="dashed" disabled={runLoading || submitLoading} onClick={() => {}}><HistoryOutlined />History</Button>
                </Flex>
                <Flex gap={4}>
                    <Flex 
                    vertical={true}
                    className="h-[800px] w-[40%] drop-shadow"
                    gap={4}>
                        <div className="h-[450px]">
                            <Card title={data.title} className="h-full w-full rounded-none overflow-y-scroll scrollbar-thin">
                                <p><strong>Độ khó: </strong><Tag className="mx-2" key='1' color={getTopicColor(data.difficulty)}>{data.difficulty}</Tag></p>
                                <p><strong>Mô tả:</strong></p>
                                <Typography className="mt-2">
                                    <div dangerouslySetInnerHTML={{ __html:data.description }} style={{wordBreak: "break-word", whiteSpace: "pre-wrap"}} />
                                </Typography>
                                <p><strong>Ví dụ:</strong></p>
                                <Typography className="mt-2">
                                    <div dangerouslySetInnerHTML={{ __html:data.example }} style={{wordBreak: "break-word", whiteSpace: "pre-wrap"}} />
                                </Typography>
                            </Card>
                        </div>
                        <div className="h-[250px]">
                            <Card title={
                                `Ouput: ${runResult?.submitError
                                    ? runResult.submitError
                                    : runResult?.submitResult ? runResult.submitResult
                                    : ''}`
                            } className="h-full w-full rounded-none overflow-y-scroll scrollbar-thin">
                                <pre><strong>Stdout: </strong>{runResult?.stdout}</pre>
                                <pre><strong>Thời gian: </strong>{runResult?.time} giây</pre>
                                <pre><strong>Dung lượng: </strong>{runResult?.memory} KB</pre>
                            </Card>
                        </div>
                    </Flex>
                    <div className="h-[704px] w-[60%] drop-shadow">
                        <Card title="Code Editor" className="h-full w-full rounded-none overflow-hidden" styles={{ body: { height: "100%", padding: "24px 0" } }}>
                            <MonacoEditor language={language} code={code} setCode={setCode} />
                        </Card>
                    </div>
                </Flex>

                <Modal
                    title={
                        <div className="flex items-center">
                            <CheckCircleOutlined className="w-6 h-6 text-green-500 mr-2" />
                            <Typography.Text strong className="text-lg">Kết quả submit</Typography.Text>
                        </div>
                    }
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                >
                    <div className="p-2">
                        {/* Kết quả tổng quát */}
                        <div className="mb-4">
                            <Typography.Text className="font-semibold">Kết quả:</Typography.Text>{" "}
                            <span
                                className={`inline-block px-3 py-1 rounded-full text-white ${
                                    submitResult?.result === "Kết quả hợp lệ"
                                        ? "bg-green-500"
                                        : "bg-red-500"
                                }`}
                            >
                                {submitResult?.result}
                            </span>
                        </div>

                        <Divider />

                        {/* Điểm và thời gian */}
                        <div className="mb-4">
                            <Typography.Text className="font-semibold">Score:</Typography.Text>{" "}
                            <span className="text-gray-700">{submitResult?.score}</span>
                        </div>
                        <div className="mb-4 flex items-center">
                            <ClockCircleOutlined className="w-5 h-5 text-blue-500 mr-2" />
                            <Typography.Text className="font-semibold">Time:</Typography.Text>{" "}
                            <span className="ml-2 text-gray-700">{submitResult?.time} giây</span>
                        </div>

                        <Divider />

                        {/* Chi tiết test case */}
                        <Typography.Text className="font-semibold">Test Case Details:</Typography.Text>
                        {submitResult?.submissionResults?.map((test, index) => (
                            <div
                                key={test.id}
                                className="mb-4 p-3 border rounded-md shadow-sm bg-gray-50"
                            >
                                <p className={`font-semibold ${test.submitResult === "Kết quả hợp lệ" ? "text-blue-600" : "text-red-500"}`}>Test Case {index + 1}</p>
                                <p>
                                    <span className="font-semibold">Time:</span>{" "}
                                    <span className="text-gray-700">{test.submitResult}</span>
                                </p>
                                <p>
                                    <span className="font-semibold">Time:</span>{" "}
                                    <span className="text-gray-700">{test.time} giây</span>
                                </p>
                                <p>
                                    <span className="font-semibold">Memory:</span>{" "}
                                    <span className="text-gray-700">{test.memory} KB</span>
                                </p>
                            </div>
                        ))}
                    </div>
                </Modal>
            </>
        )
    )
}