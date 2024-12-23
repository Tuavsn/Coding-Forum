'use client'

import { Button, Card, Col, Divider, Drawer, Form, Input, List, message, Popconfirm, Row, Select, Space, Spin, Tag, Typography } from "antd";
import React, { useContext, useEffect, useState } from "react";
import { PlayCircleOutlined, MessageOutlined, ClockCircleOutlined, LoadingOutlined, PlusOutlined, SettingOutlined, UserOutlined } from "@ant-design/icons";
import Link from "next/link";
import { Problem } from "@/libs/types";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { createProblem, deleteProblem, getProblem, updateProblem } from "@/libs/actions/problem.actions";
import { formatDate } from "@/libs/utils";
import { AntdIconProps } from '@ant-design/icons/lib/components/AntdIcon';
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { ProblemType } from "@/libs/enum";
import { AuthContext } from "@/context/AuthContextProvider";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import dynamic from "next/dynamic";

function stringToSlug(str: string) {
   // Chuyển tất cả các ký tự thành chữ thường
   str = str.toLowerCase();

   // Thay thế các ký tự đặc biệt tiếng Việt
   str = str.replace(/đ/g, 'd');

   // Loại bỏ dấu tiếng Việt
   str = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");

   // Thay thế các ký tự không phải chữ cái hoặc số bằng dấu gạch ngang
   str = str.replace(/[^a-z0-9\s-]/g, '');

   // Thay thế khoảng trắng hoặc dấu gạch ngang liên tiếp bằng một dấu gạch ngang
   str = str.trim().replace(/\s+/g, '-').replace(/-+/g, '-');

   return str;
}

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

const IconText = ({ icon, text }: { icon: React.ComponentType<AntdIconProps>; text: string }) => (
    <Space>
        {React.createElement(icon, {style: {fontSize: '18px'}})}
        {text}
    </Space>
);

export default function ProblemList() {
    const {auth, setAuth} = useContext(AuthContext);

    const { data, isLoading } = useQuery<Problem[]>('getProblem', getProblem);

    const queryClient = useQueryClient();

    const [openDrawer, setOpenDrawer] = useState(false);

    const [problemCreateLoading, setProblemCreateLoading] = useState(false);

    const [problemUpdateLoading, setProblemUpdateLoading] = useState(false);

    const [problemDeleteLoading, setProblemDeleteLoading] = useState(false);

    const [problemId, setProblemId] = useState('');

    const [problemTitle, setProblemTitle] = useState('');

    const [problemDescription, setProblemDescription] = useState('');

    const [example, setExample] = useState('');

    const [tags, setTags] = useState('');

    const [difficulty, setDifficulty] = useState(ProblemType.EASY);

    const [testCases, setTestCases] = useState('');

    const [totalScore, setTotalScore] = useState(0);

    const [form] = Form.useForm();

    // create problem
    const problemCreateMutation = useMutation(createProblem, {
        onMutate: () => {
            setProblemCreateLoading(true);
        },

        onSuccess: (data) => {
            setProblemCreateLoading(false);
            setOpenDrawer(false);
            queryClient.invalidateQueries('getProblem');
            setProblemTitle('');
            setProblemDescription('');
            setExample('');
            setTags('');
            setDifficulty(ProblemType.EASY);
            setTestCases('');
            setTotalScore(0);
            message.success(data.Message);
        }
    })

    const showCreateProblemDrawer = () => {
        setOpenDrawer(true);
    };

    const handleCreateProblem = async () => {
        try {
            await form.validateFields();
            problemCreateMutation.mutate({
                newProblem: {
                    title: problemTitle,
                    description: problemDescription,
                    example: example,
                    tags: tags,
                    difficulty: difficulty,
                    testCases: testCases,
                    totalScore: totalScore
                }
            })
        } catch (error: any) {
        }
    }

    // update problem
    const problemUpdateMutation = useMutation(updateProblem, {
        onMutate: () => {
            setProblemUpdateLoading(true);
        },

        onSuccess: (data) => {
            setProblemUpdateLoading(false);
            setOpenDrawer(false);
            queryClient.invalidateQueries('getProblem');
            setProblemId('');
            setProblemTitle('');
            setProblemDescription('');
            setExample('');
            setTags('');
            setDifficulty(ProblemType.EASY);
            setTestCases('');
            setTotalScore(0);
            message.success(data.Message);
        }
    })

    const showUpdateProblemDrawer = (problem:Problem) => {
        setOpenDrawer(true);
        setProblemId(problem.id);
        setProblemTitle(problem.title);
        setProblemDescription(problem.description);
        setExample(problem.example);
        setTags(problem.tags);
        setDifficulty(problem.difficulty);
        setTestCases(problem.testCases);
        setTotalScore(problem.totalScore);
    }

    const handleUpdateProblem = async () => {
        try {
            await form.validateFields();
            problemUpdateMutation.mutate({
                newProblem: {
                    title: problemTitle,
                    description: problemDescription,
                    example: example,
                    tags: tags,
                    difficulty: difficulty,
                    testCases: testCases,
                    totalScore: totalScore
                }
            })
        } catch (error: any) {
        }
    }

    // delete problem
    const problemDeleteMutation = useMutation(deleteProblem, {
        onMutate: () => {
            setProblemDeleteLoading(true);
        },

        onSuccess: (data) => {
            setProblemDeleteLoading(false);
            queryClient.invalidateQueries('getProblem');
            message.success(data.Message);
        }
    })

    const handleDeleteProblem = (problemId: string) => {
        problemDeleteMutation.mutate(problemId);
    }

    // common
    const closeDrawer = () => {
        setOpenDrawer(false);
    }

    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    return (
        <>
        {isLoading ? (
            <Spin indicator={<LoadingOutlined style={{ fontSize: 48 }} spin />} />
        ) : (
            <>
                {
                    auth && auth.role === 'SYS_ADMIN' && (
                        <Button type="primary" onClick={showCreateProblemDrawer}><PlusOutlined />Thêm bài</Button>
                    )
                }
                <Divider orientation="left"><p>{data?.length} Bài tập</p></Divider>
                <Card>
                    <List
                        size="large"
                        className="mb-8"
                        bordered={false}
                        split={false}
                        itemLayout="vertical" 
                        pagination={{
                            pageSize: 10
                        }}
                        dataSource={data} 
                        renderItem={(item) => (
                            <List.Item
                                className="px-0"
                                actions={[
                                    <Tag key='1' color={getTopicColor(item.difficulty)}>{item.difficulty}</Tag>,
                                    <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                        <IconText icon={PlayCircleOutlined} text="232" key="list-vertical-message" />
                                    </Button>,
                                    <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                        <IconText icon={MessageOutlined} text="244" key="list-vertical-message" />
                                    </Button>,
                                    auth?.username == item.author.username ? (
                                        <Popconfirm
                                            title="Tuỳ chọn"
                                            onConfirm={() => handleDeleteProblem(item.id)}
                                            onCancel={() => showUpdateProblemDrawer(item)}
                                            okText="Xoá"
                                            cancelText="Chỉnh sửa"
                                            key="list-vertical-message"
                                        >
                                            <Button className="border-none px-2 shadow-none"><IconText icon={SettingOutlined} text=""/></Button>
                                        </Popconfirm>
                                    ) : <></>
                                ]}
                            >
                                <List.Item.Meta
                                    title={<Link href={`/problem/${stringToSlug(item.title)}?id=${item.id}`}><strong>{item.title}</strong></Link>}
                                    description={(
                                        <>
                                            <strong className="mr-6"><Link href={`/user?id=${item.author.id}`}><UserOutlined /> {item.author.username}</Link></strong>
                                            <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                        </>
                                    )}
                                />
                                <Typography className="relative max-h-[160px] overflow-hidden">
                                    <div 
                                        dangerouslySetInnerHTML={{ __html: item.description }} 
                                        className="break-words"
                                    />
                                </Typography>
                            </List.Item>
                        )}
                    />
                </Card>
                <Drawer
                    width={720}
                    onClose={closeDrawer}
                    open={openDrawer}
                    styles={{
                    body: {
                        paddingBottom: 80,
                    },
                    }}
                    extra={
                    <Space>
                        <Button onClick={closeDrawer}>Hủy</Button>
                        {problemId === '' ? (
                            <Button onClick={handleCreateProblem} type="primary" loading={problemCreateLoading}>
                                Thêm
                            </Button>
                        ) : (
                            <Button onClick={handleUpdateProblem} type="primary" loading={problemUpdateLoading}>
                                Cập nhật
                            </Button>
                        )}
                    </Space>
                    }
                >
                    <Form form={form} layout="vertical">
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    name="title"
                                    label="Tiêu đề"
                                    rules={[{ required: true, message: 'Nhập tiêu đề bài Post' }]}
                                >
                                    <Input style={{width: '100%'}} onChange={(e) => setProblemTitle(e.target.value)} value={problemTitle} placeholder="Nhập tiêu đề" />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    label="Nội dung"
                                    rules={[
                                    {
                                        required: true,
                                        message: 'Nhập nội dung',
                                    },
                                    ]}
                                >
                                    <CKEditor
                                        editor={ ClassicEditor }
                                        onChange={(event, editor) => setProblemDescription(editor.getData())}
                                        data={problemDescription}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    label="Ví dụ"
                                    rules={[
                                    {
                                        required: true,
                                        message: 'Nhập ví dụ',
                                    },
                                    ]}
                                >
                                    <CKEditor
                                        editor={ ClassicEditor }
                                        onChange={(event, editor) => setExample(editor.getData())}
                                        data={example}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    name="tag"
                                    label="Tags"
                                    rules={[{ required: true, message: 'Nhập tags' }]}
                                >
                                    <Input style={{width: '100%'}} onChange={(e) => setTags(e.target.value)} value={tags} placeholder="Nhập tags" />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    label="Độ khó"
                                    rules={[{ required: true, message: 'Chọn độ khó' }]}
                                >
                                    <Select
                                        style={{ width: '100%' }} 
                                        value={difficulty} 
                                        onChange={(value) => setDifficulty(value)} 
                                        placeholder="Chọn độ khó"
                                    >
                                        <Select.Option value={ProblemType.EASY}>Dễ</Select.Option>
                                        <Select.Option value={ProblemType.MEDIUM}>Trung bình</Select.Option>
                                        <Select.Option value={ProblemType.HARD}>Khó</Select.Option>
                                    </Select>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    name="testcase"
                                    label="Test cases"
                                    rules={[{ required: true, message: 'Nhập test cases. Vd: 1,1;2|2,2;4 Với input: 1, 1; 2, 2 và output: 2; 4' }]}
                                >
                                    <Input style={{width: '100%'}} onChange={(e) => setTestCases(e.target.value)} value={testCases} placeholder="Nhập test cases" />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    name="totalscore"
                                    label="Tổng điểm"
                                    rules={[{ required: true, message: 'Nhập tổng điểm' }]}
                                >
                                    <Input style={{width: '100%'}} onChange={(e) => setTotalScore(Number(e.target.value))} value={totalScore} placeholder="Nhập tổng điểm" />
                                </Form.Item>
                            </Col>
                        </Row>
                    </Form>
                </Drawer>
            </>
            )}
        </>
    )
}