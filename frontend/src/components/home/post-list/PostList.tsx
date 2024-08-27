'use client'

import { createPost } from "@/libs/actions/post.acttion";
import { formatDate, stringToSlug } from "@/libs/utils";
import { ClockCircleOutlined, DislikeOutlined, HeartOutlined, LikeOutlined, MessageOutlined, PlusOutlined, RightCircleOutlined, SmileOutlined, UserOutlined } from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, Card, Col, Divider, Drawer, Form, Image, Input, List, Row, Space } from "antd";
import Link from "next/link";
import React, { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";

interface postList {
    posts: Post[];
    topic: Topic;
}

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);


export default function PostList({posts, topic}: postList) {
    const queryClient = useQueryClient()

    const sortedPost = [...posts].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

    const [username, setUsername] = useState<string | null>(sessionStorage.getItem('username'))

    const [openDrawer, setOpenDrawer] = useState(false)

    const [loading, setLoading] = useState(false)

    const [postHeader, setPostHeader] = useState('')

    const [postContent, setPostContent] = useState('')

    useQuery('getUsername', () => setUsername(sessionStorage.getItem('username')))

    const mutation = useMutation(createPost, {
        onMutate: () => {
            setLoading(true)
        },

        onSuccess: () => {
            setLoading(false)
            setOpenDrawer(false)
            queryClient.invalidateQueries('getTopic')
            setPostHeader('')
            setPostContent('')
        }
    })

    const showDrawer = () => {
        setOpenDrawer(true);
    };
    
    const closeDrawer = () => {
        setOpenDrawer(false);
        setPostHeader('')
        setPostContent('')
    };

    const handleSetLoading = () => {
        mutation.mutate({
            topic: {
                id: topic.id
            },
            header: postHeader,
            content: postContent
        })
    }

    return (
        <>
            <Card>
                {
                    username && (
                        <Button type="primary" onClick={showDrawer}><PlusOutlined /> Thêm Post mới</Button>
                    )
                }
                <Divider orientation="left"><p>{sortedPost.length} Bài Post</p></Divider>
                <List
                    size="large"
                    className="my-6"
                    bordered={false}
                    split={false}
                    itemLayout="vertical" 
                    pagination={{
                        pageSize: 10
                    }}
                    dataSource={sortedPost} 
                    renderItem={(item) => (
                        <List.Item
                            className="px-0"
                            actions={[
                                <Button className="border-none" key="list-vertical-message"><IconText icon={HeartOutlined} text="222"/></Button>,
                                <Button className="border-none" key="list-vertical-message"><IconText icon={SmileOutlined} text="242"/></Button>,
                                <Button className="border-none" key="list-vertical-message"><IconText icon={LikeOutlined} text="333"/></Button>,
                                <Button className="border-none" key="list-vertical-message"><IconText icon={DislikeOutlined} text="232"/></Button>,
                                <Button className="border-none" key="list-vertical-message"><IconText icon={MessageOutlined} text="244"/></Button>
                            ]}
                            extra={
                                item.postImage && (
                                    <Image 
                                        width={300}
                                        alt={item.postImage[0]?.image}
                                        src={item.postImage[0]?.image}
                                    />
                                )
                            }
                        >
                            <List.Item.Meta
                                avatar={<Avatar shape="square" size={60} src={item.user.avatar} />}
                                title={<Link href={`/post/${stringToSlug(topic.name)}}/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                                description={(
                                    <>
                                        <strong className="mr-6"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                        <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                    </>
                                )}
                            />
                            <div dangerouslySetInnerHTML={{ __html: item.content }} style={{maxHeight: "160px", overflow: "hidden"}} />
                        </List.Item>
                    )}
                />
            </Card>

            <Drawer
                title="Thêm bình luận mới"
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
                    <Button onClick={handleSetLoading} type="primary" loading={loading}>
                        Thêm
                    </Button>
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
                                <Input style={{width: '100%'}} onChange={(e) => setPostHeader(e.target.value)} value={postHeader} placeholder="Nhập tiêu đề bài Post" />
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
                                    onChange={(event, editor) => setPostContent(editor.getData())}
                                    data={postContent}
                                />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    )
}