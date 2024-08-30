'use client'

import { uploadImage } from "@/libs/actions/image.action";
import { createPost, deletePost } from "@/libs/actions/post.acttion";
import { formatDate, stringToSlug } from "@/libs/utils";
import { ClockCircleOutlined, DislikeOutlined, HeartOutlined, LikeOutlined, MessageOutlined, PlusOutlined, RightCircleOutlined, SettingOutlined, SmileOutlined, UploadOutlined, UserOutlined } from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, Card, Col, Divider, Drawer, Form, Image, Input, List, message, Popconfirm, Row, Space, Upload } from "antd";
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

    const [postCreateLoading, setPostCreateLoading] = useState(false)

    const [postDeleteLoading, setPostDeleteLoading] = useState(false)

    const [postHeader, setPostHeader] = useState('')

    const [postContent, setPostContent] = useState('')

    const [upload, setUpload] = useState([])

    const [postImage, setPostImage] = useState('')

    useQuery('getUsername', () => setUsername(sessionStorage.getItem('username')))

    const postCreateMutation = useMutation(createPost, {
        onMutate: () => {
            setPostCreateLoading(true)
        },

        onSuccess: (data) => {
            setPostCreateLoading(false)
            setOpenDrawer(false)
            queryClient.invalidateQueries('getTopic')
            setPostHeader('')
            setPostContent('')
            setPostImage('')
            message.success(data.Message)
        }
    })

    const postDeleteMutation = useMutation(deletePost, {
        onMutate: () => {
            setPostDeleteLoading(true)
        },

        onSuccess: (data) => {
            setPostDeleteLoading(false)
            queryClient.invalidateQueries('getTopic')
            message.success(data.Message)
        }
    })

    const showDrawer = () => {
        setOpenDrawer(true);
    };
    
    const closeDrawer = () => {
        setOpenDrawer(false);
        setPostHeader('')
        setPostContent('')
        setPostImage('')
    };

    const handleFileChange = async(selectedFile: File) => {
        const formData = new FormData()
        formData.append('file', selectedFile)
        formData.append('upload_preset', 'studentcodehub_preset');
        setPostImage(await uploadImage(formData)) 
    };

    const handleDeletePost = (id: string) => {
        postDeleteMutation.mutate(id)
    }

    const handleCreatePost = () => {
        postCreateMutation.mutate({
            topic: {
                id: topic.id
            },
            header: postHeader,
            content: postContent,
            postImage: [
                {image: postImage}
            ]
        })
    }

    return (
        <>
            {
                username && (
                    <Button type="primary" onClick={showDrawer}><PlusOutlined /> Thêm Post mới</Button>
                )
            }
            <Divider orientation="left"><p>{sortedPost.length} Bài Post</p></Divider>
            <Card>
                <List
                    size="large"
                    className="my-2"
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
                                <Button className="border-none px-2" key="list-vertical-message"><IconText icon={HeartOutlined} text="222"/></Button>,
                                <Button className="border-none px-2" key="list-vertical-message"><IconText icon={SmileOutlined} text="242"/></Button>,
                                <Button className="border-none px-2" key="list-vertical-message"><IconText icon={LikeOutlined} text="333"/></Button>,
                                <Button className="border-none px-2" key="list-vertical-message"><IconText icon={DislikeOutlined} text="232"/></Button>,
                                <Button className="border-none px-2" key="list-vertical-message"><IconText icon={MessageOutlined} text="244"/></Button>,
                                <Popconfirm 
                                    title="Tuỳ chọn"
                                    onConfirm={() => handleDeletePost(item.id)}
                                    // onCancel={cancel}
                                    okText="Xoá"
                                    cancelText="Chỉnh sửa"
                                    key="list-vertical-message"
                                >
                                    <Button className="border-none px-2"><IconText icon={SettingOutlined} text=""/></Button>
                                </Popconfirm>
                            ]}
                            extra={
                                item.postImage && (
                                    <Image 
                                        width={300}
                                        height={300}
                                        style={{objectFit: "contain"}}
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
                title="Thêm bài Post mới"
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
                    <Button onClick={handleCreatePost} type="primary" loading={postCreateLoading}>
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
                    <Row gutter={16}>
                        <Col span={22}>
                            <Form.Item
                                label="Hình ảnh"
                                rules={[
                                {
                                    required: true,
                                    message: 'Upload ảnh bài viết',
                                },
                                ]}
                            >
                                <Upload beforeUpload={handleFileChange} maxCount={1}>
                                    <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
                                </Upload>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    )
}