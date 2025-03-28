'use client'
import { CheckCircleOutlined, ClockCircleOutlined, CloseCircleOutlined, CloudServerOutlined, DatabaseOutlined, HistoryOutlined, PlayCircleOutlined } from "@ant-design/icons";
import { Button, Card, Collapse, Divider, Flex, message, Modal, Pagination, Skeleton, Splitter, Tag, Typography } from "antd";
import LanguageMenu from "./LanguageMenu";
import MonacoEditor from "./MonacoEditor";
import { useContext, useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { AuthContext } from "@/context/AuthContextProvider";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { getProblemDetail, getSubmission, runSolution, submitSolution } from "@/libs/actions/problem.actions";
import { Problem, ProblemSubmission, SubmissionResult } from "@/libs/types";
import { ProblemSubmissionLanguageType, ProblemType } from "@/libs/enum";
import ThemeMenu from "./ThemeMenu";

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

const { Panel } = Collapse;

export default function ProblemDetail() {
    const { auth } = useContext(AuthContext);

    const problemId = useSearchParams().get('id');

    const { data, isLoading } = useQuery<Problem>(
        ['getProblemDetail', problemId], () => getProblemDetail(problemId)
    );

    const [runLoading, setRunLoading] = useState(false);

    const [submitLoading, setSubmitLoading] = useState(false);

    const [historyLoading, setHistoryLoading] = useState(false);

    const [code, setCode] = useState('');

    const [language, setLanguage] = useState(ProblemSubmissionLanguageType.CPLUSPLUS);

    const [runResult, setRunResult] = useState<SubmissionResult>();
    
    const [submitResult, setSubmitResult] = useState<ProblemSubmission>();

    const [submissions, setSubmissions] = useState<ProblemSubmission[]>();

    const [isSubmitModalVisible, setIsSubmitModalVisible] = useState(false);
    
    const [isHistoryModalVisible, setIsHistoryModalVisible] = useState(false);

    const [currentPage, setCurrentPage] = useState(1); // Initialize page to 1

    const [editorTheme, setEditorTheme] = useState("vs-light");

    const [bgColor, setBgColor] = useState("#1E1E1E");

    const [textColor, setTextColor] = useState("white");

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
            setIsSubmitModalVisible(true);
            setSubmitLoading(false);
        }
    })

    const handleSubmitSolution = () => {
        if (auth) {
            submitSolutionMutation.mutate({
                problemId: problemId as string,
                solution: {
                    languageType: language,
                    code: code
                }
            });
        } else {
            message.error("Bạn cần đăng nhập để submit bài giải");
        }
    }

    const handleSubmitModalConfirm = () => {
        setIsSubmitModalVisible(false);
    };

    // get history
    const getSubmissionMutation = useMutation(getSubmission, {
        onMutate: () => {
            setHistoryLoading(true);
        },

        onSuccess: (data) => {
            setSubmissions(data);
            setIsHistoryModalVisible(true);
            setHistoryLoading(false);
        }
    })

    const handleGetSubmissions = () => {
        if (auth) {
            getSubmissionMutation.mutate(problemId as string);
        } else {
            message.error("Bạn cần đăng nhập để xem lịch sử nộp bài");
        }
    }

    const handleHistoryModalConfirm = () => {
        setIsHistoryModalVisible(false);
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page); // Update the current page when the user clicks a page number
    };

    const handleThemeChange = (theme: string) => {
        if (theme === "vs-dark") {
            setEditorTheme("vs-dark");
            setBgColor("bg-[#1E1E1E]");
            setTextColor("text-white");
        } else {
            setEditorTheme("vs-light");
            setBgColor("bg-white");
            setTextColor("text-black");
        }
    }

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [problemId]);

    
    if(isLoading) {
        return <Skeleton active />
    }
    return (
        data && (
            <>
                {/* Buttons */}
                <Flex gap={6} justify="center" className="m-4">
                    <Button size="large" type="primary" loading={runLoading} disabled={runLoading || submitLoading} onClick={handleRunSolution}><PlayCircleOutlined /><p>Run</p></Button>
                    <Button size="large" type="primary" loading={submitLoading} disabled={runLoading || submitLoading} onClick={handleSubmitSolution}><CloudServerOutlined /><p>Submit</p></Button>
                    <LanguageMenu language={language} setLanguage={setLanguage} />
                    <ThemeMenu editorTheme={editorTheme} onThemeChange={handleThemeChange} />
                    <Button size="large" type="dashed" loading={historyLoading} disabled={runLoading || submitLoading} onClick={handleGetSubmissions}><HistoryOutlined />History</Button>
                </Flex>

                {/* Main Editor */}
                <Splitter
                // vertical={true}
                className="h-[800px] w-full drop-shadow">
                    <Splitter.Panel>
                        <div className="h-[70%]">
                            <Card title={<span className={`${textColor} break-words whitespace-pre-wrap`}>{data.title}</span>} className={`h-full w-full rounded-none overflow-y-scroll scrollbar-thin ${bgColor}`}>
                                <p className={`${textColor}`}><strong>Độ khó: </strong><Tag className="mx-2" key='1' color={getTopicColor(data.difficulty)}>{data.difficulty}</Tag></p>
                                <p className={`${textColor}`}><strong>Mô tả:</strong></p>
                                <Typography className={`mt-2 ${textColor}`}>
                                    <div dangerouslySetInnerHTML={{ __html:data.description }} style={{wordBreak: "break-word", whiteSpace: "pre-wrap"}} />
                                </Typography>
                                <p className={`${textColor}`}><strong>Ví dụ:</strong></p>
                                <Typography className={`mt-2 ${textColor}`}>
                                    <div dangerouslySetInnerHTML={{ __html:data.example }} className="ck-content break-words whitespace-pre-wrap" />
                                </Typography>
                            </Card>
                        </div>
                        <div className="h-[30%]">
                            <Card title={
                                <span className={`${textColor} break-words whitespace-pre-wrap`}>
                                    Kết quả: {runResult?.submitError
                                    ? runResult.submitError
                                    : runResult?.submitResult ? runResult.submitResult
                                    : ''}
                                </span>
                            } className={`h-full w-full rounded-none overflow-y-scroll scrollbar-thin ${bgColor}`}>
                                <pre className={`${textColor}`}><strong>Stdout: </strong>{runResult?.stdout}</pre>
                                <pre className={`${textColor}`}><strong>Thời gian: </strong>{runResult?.time} giây</pre>
                                <pre className={`${textColor}`}><strong>Bộ nhớ: </strong>{runResult?.memory} KB</pre>
                            </Card>
                        </div>
                    </Splitter.Panel>
                    <Splitter.Panel defaultSize="60%" min="20%" max="70%">
                        <div className="h-full w-full drop-shadow">
                            <Card title={<span className={`${textColor}`}>Code Editor</span>} className={`h-full w-full rounded-none overflow-hidden ${bgColor}`} styles={{ body: { height: "100%", padding: "24px 0" } }}>
                                <MonacoEditor theme={editorTheme} language={language.toLowerCase()} code={code} setCode={setCode} />
                            </Card>
                        </div>
                    </Splitter.Panel>
                </Splitter>

                {/* Modal submit result  */}
                <Modal
                    title={
                        <div className="flex items-center">
                            <CheckCircleOutlined className="w-6 h-6 text-green-500 mr-2" />
                            <Typography.Text strong className="text-lg">Kết quả submit</Typography.Text>
                        </div>
                    }
                    open={isSubmitModalVisible}
                    onCancel={handleSubmitModalConfirm}
                    footer={null}
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
                            <Typography.Text className="font-semibold">Tổng điểm:</Typography.Text>{" "}
                            <span className="text-gray-700">{submitResult?.score}</span>
                        </div>
                        <div className="mb-4 flex items-center">
                            <ClockCircleOutlined className="w-5 h-5 text-blue-500 mr-2" />
                            <Typography.Text className="font-semibold">Thời gian trung bình:</Typography.Text>{" "}
                            <span className="ml-2 text-gray-700">{submitResult?.time.toFixed(3)} giây</span>
                        </div>

                        <div className="mb-4 flex items-center">
                            <DatabaseOutlined className="w-5 h-5 text-blue-500 mr-2" />
                            <Typography.Text className="font-semibold">Bộ nhớ trung bình:</Typography.Text>{" "}
                            <span className="ml-2 text-gray-700">{submitResult?.memory.toFixed(3)} KB</span>
                        </div>

                        <Divider />

                        {/* Chi tiết test case */}
                        <Typography.Text className="font-semibold">Chi tiết Test Case:</Typography.Text>
                        {submitResult?.submissionResults?.map((test, index) => (
                            <div
                                key={test.id}
                                className="mb-4 p-3 border rounded-md shadow-sm bg-gray-50"
                            >
                                <p className={`font-semibold ${test.submitResult === "Kết quả hợp lệ" ? "text-blue-600" : "text-red-500"}`}>Test Case {index + 1}</p>
                                <p>
                                    <span className="font-semibold">Kết quả:</span>{" "}
                                    <span className="text-gray-700">{test.submitResult}</span>
                                </p>
                                <p>
                                    <span className="font-semibold">Thời gian:</span>{" "}
                                    <span className="text-gray-700">{test.time.toFixed(3)} giây</span>
                                </p>
                                <p>
                                    <span className="font-semibold">Bộ nhớ:</span>{" "}
                                    <span className="text-gray-700">{test.memory.toFixed(3)} KB</span>
                                </p>
                            </div>
                        ))}
                    </div>
                </Modal>

                {/* Modal History */}
                <Modal
                    title={<Typography.Text strong className="text-lg">Tổng số lần submit: ({submissions?.length})</Typography.Text>}
                    open={isHistoryModalVisible}
                    onCancel={handleHistoryModalConfirm}
                    width={800} // Increased width for better layout
                    footer={null} // No footer buttons
                >
                    {historyLoading ? (
                        <Skeleton active />
                    ) : (
                        <div className="overflow-y-auto max-h-[500px]">
                            {submissions?.length === 0 ? (
                                <Typography.Text className="text-gray-500">Chưa có kết quả nào</Typography.Text>
                            ) : (
                                <div>
                                    {/* Display each submission inside a Collapse component */}
                                    <Collapse accordion>
                                        {submissions?.slice((currentPage - 1) * 5, currentPage * 5).map((submission) => (
                                            <Panel
                                                key={submission.id}
                                                header={
                                                    <div className="flex justify-between items-center">
                                                        <Typography.Text className="flex items-center">
                                                            {submission.result === "Kết quả hợp lệ" ? (
                                                                <CheckCircleOutlined className="text-green-500 mr-2" />
                                                            ) : (
                                                                <CloseCircleOutlined className="text-red-500 mr-2" />
                                                            )}
                                                            <strong>{submission.result}</strong>
                                                        </Typography.Text>
                                                    </div>
                                                }
                                            >
                                                <div className="text-sm">
                                                    <div className="mb-2">
                                                        <Typography.Text className="font-semibold">Thời gian thực thi:</Typography.Text>{" "}
                                                        <span className="text-gray-700">{submission.time} giây</span>
                                                    </div>
                                                    <div className="mb-2">
                                                        <Typography.Text className="font-semibold">Bộ nhớ sử dụng:</Typography.Text>{" "}
                                                        <span className="text-gray-700">{submission.memory} KB</span>
                                                    </div>
                                                    <div className="mb-2">
                                                        <Typography.Text className="font-semibold">Ngôn ngữ:</Typography.Text>{" "}
                                                        <span className="text-gray-700">{submission.languageType}</span>
                                                    </div>

                                                    {/* Displaying details of each submissionResult */}
                                                    <Typography.Text className="font-semibold">Chi tiết các Test Case:</Typography.Text>
                                                    {submission.submissionResults?.sort((a, b) => a.testCaseNum - b.testCaseNum).map((testCase, index) => (
                                                        <div key={testCase.id} className="mb-4 p-3 border rounded-md shadow-sm bg-gray-50">
                                                            <div className="flex justify-between">
                                                                <Typography.Text className="font-semibold text-gray-700">Test Case {testCase.testCaseNum}</Typography.Text>
                                                                <Typography.Text
                                                                    className={`font-semibold ${testCase.submitResult === "Kết quả hợp lệ" ? "text-blue-600" : "text-red-500"}`}
                                                                >
                                                                    {testCase.submitResult}
                                                                </Typography.Text>
                                                            </div>
                                                            <div className="mt-2">
                                                                <p><strong>Stdout:</strong> {testCase.stdout || "Không có đầu ra"}</p>
                                                                <p><strong>Thời gian:</strong> {testCase.time} giây</p>
                                                                <p><strong>Bộ nhớ:</strong> {testCase.memory} KB</p>
                                                            </div>
                                                            {testCase.submitError && (
                                                                <div className="mt-2 p-2 bg-red-100 text-red-700 rounded-md">
                                                                    <strong>Lỗi: </strong> {testCase.submitError}
                                                                </div>
                                                            )}
                                                        </div>
                                                    ))}
                                                </div>
                                            </Panel>
                                        ))}
                                    </Collapse>

                                    {/* Pagination for submissions */}
                                    <Pagination
                                        className="mt-4"
                                        total={submissions?.length}
                                        pageSize={5}
                                        onChange={handlePageChange}
                                        showSizeChanger={false}
                                        current={currentPage}
                                        showTotal={(total) => `Tổng ${total} kết quả`}
                                    />
                                </div>
                            )}
                        </div>
                    )}
                </Modal>
            </>
        )
    )
}