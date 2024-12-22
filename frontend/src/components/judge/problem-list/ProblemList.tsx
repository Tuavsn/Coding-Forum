'use client'

import { Button, Card, Col, Divider, Drawer, Form, Input, List, message, Popconfirm, Row, Select, Space, Spin, Tag, Typography } from "antd";
import React, { useContext, useState } from "react";
import { PlayCircleOutlined, MessageOutlined, ClockCircleOutlined, LoadingOutlined, PlusOutlined, SettingOutlined } from "@ant-design/icons";
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
import { Option } from "antd/es/mentions";

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

const dataSource = [
    {
        name: "Dễ",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    },
    {
        name: "Trung bình",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Word',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Excel',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'PowerPoint',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Zalo',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    },
    {
        name: "Khó",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C#',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Javascript',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    }
];

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

    const handleCreateProblem = () => {
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

    const handleUpdateProblem = () => {
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
                                    <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                        <IconText icon={ClockCircleOutlined} text={formatDate(item.createdAt.toString())} />
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
                    <Form layout="vertical" hideRequiredMark>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
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
                                        message: 'Nhập nội dung bài Post',
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
                                        <Option value={ProblemType.EASY}>Dễ</Option>
                                        <Option value={ProblemType.MEDIUM}>Trung bình</Option>
                                        <Option value={ProblemType.HARD}>Khó</Option>
                                    </Select>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
                                    label="Test cases"
                                    rules={[{ required: true, message: 'Nhập test cases' }]}
                                >
                                    <Input style={{width: '100%'}} onChange={(e) => setTestCases(e.target.value)} value={testCases} placeholder="Nhập test cases" />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={22}>
                                <Form.Item
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