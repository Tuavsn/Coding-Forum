'use client'

import { createComment } from "@/libs/actions/post.acttion";
import { formatDate } from "@/libs/utils";
import { 
    ClockCircleOutlined,
    DislikeOutlined,
    HeartOutlined,
    LikeOutlined,
    PlusOutlined,
    SmileOutlined,
    UserOutlined 
} from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, List, Space, Form, Divider, Drawer, Row, Col } from "antd";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function Comment({post}:{post: Post}) {
    const queryClient = useQueryClient()

    const sortedComment = [...post.postComment].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

    const [openDrawer, setOpenDrawer] = useState(false)

    const [loading, setLoading] = useState(false)

    const [username, setUsername] = useState<string | null>(sessionStorage.getItem('username'))

    const [commentContent, setCommentContent] = useState('')

    useQuery('getUsername', () => setUsername(sessionStorage.getItem('username')))

    const mutation = useMutation(createComment, {
        onMutate: () => {
            setLoading(true)
        },

        onSuccess: () => {
            setLoading(false)
            setOpenDrawer(false)
            queryClient.invalidateQueries('getPostDetail')
            setCommentContent('')
        }
    })

    const showDrawer = () => {
        setOpenDrawer(true);
    };
    
    const closeDrawer = () => {
        setOpenDrawer(false);
        setCommentContent('')
    };

    const handleSetLoading = () => {
        mutation.mutate({
            post: {
                id: post.id
            },
            content: commentContent
        })
    }

    return (
        <>
            <Divider orientation="left"><p>{sortedComment.length} Bình luận</p></Divider>
            {
                username && (
                    <Button type="primary" onClick={showDrawer}><PlusOutlined /> Thêm bình luận</Button>
                )
            }
            <List
                size="large"
                bordered={false}
                className="my-6"
                split={false}
                itemLayout="vertical" 
                pagination={{
                    pageSize: 5
                }}
                dataSource={sortedComment} 
                renderItem={(item) => (
                    <List.Item className="mb-10 p-0"  
                        actions={[
                            <Button className="border-none" key="list-vertical-message"><IconText icon={HeartOutlined} text="222"/></Button>,
                            <Button className="border-none" key="list-vertical-message"><IconText icon={SmileOutlined} text="242"/></Button>,
                            <Button className="border-none" key="list-vertical-message"><IconText icon={LikeOutlined} text="333"/></Button>,
                            <Button className="border-none" key="list-vertical-message"><IconText icon={DislikeOutlined} text="232"/></Button>,
                        ]}
                    >
                        <List.Item.Meta
                            avatar={<Avatar shape="square" size={60} src={item.user?.avatar} />}
                            description={(
                                <>
                                    <strong className="mr-2 text-sm"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                    <strong className="text-sm"><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                    <p className="text-sm text-black" dangerouslySetInnerHTML={{ __html:item.content }} />
                                </>
                            )}
                        />
                    </List.Item>
                )}
            />

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
                        <Col span={24}>
                        <Form.Item
                            name="content"
                            label="Nội dung"
                            rules={[
                            {
                                required: true,
                                message: 'Nhập nội dung bình luận',
                            },
                            ]}
                        >
                            <CKEditor
                                editor={ ClassicEditor }
                                data={commentContent}
                                onChange={(event, editor) => setCommentContent(editor.getData())}
                            />
                        </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    )
}