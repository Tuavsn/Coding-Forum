'use client'

import { AuthContext } from "@/context/AuthContextProvider";
import { createComment, deleteComment, dislikeComment, likeComment, updateComment } from "@/libs/actions/post.acttion";
import { ReactionType } from "@/libs/enum";
import { Post, PostComment } from "@/libs/types";
import { formatDate } from "@/libs/utils";
import { 
    ClockCircleOutlined,
    DislikeFilled,
    DislikeOutlined,
    LikeFilled,
    LikeOutlined,
    PlusOutlined,
    SettingOutlined,
    UserOutlined 
} from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, List, Space, Form, Divider, Drawer, Row, Col, message, Card, Popconfirm, Typography } from "antd";
import Link from "next/link";
import React, { useContext, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import { AntdIconProps } from '@ant-design/icons/lib/components/AntdIcon';

const IconText = ({ icon, text }: { icon: React.ComponentType<AntdIconProps>; text: string }) => (
    <Space>
        {React.createElement(icon, {style: {fontSize: '18px'}})}
        {text}
    </Space>
);

export default function Comment({post}:{post: Post}) {
    const {auth, setAuth} = useContext(AuthContext)

    const queryClient = useQueryClient()

    const sortedComment = [...post.postComment].sort((a,b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());

    const [openDrawer, setOpenDrawer] = useState(false)

    const [createCommentLoading, setCreateCommentLoading] = useState(false)

    const [updateCommentLoading, setUpdateCommentLoading] = useState(false)

    const [deleteCommentLoading, setDeleteCommetLoading] = useState(false)

    const [commentId, setCommentId] = useState('')
    
    const [commentContent, setCommentContent] = useState('')

    // create Comment
    const createCommentMutation = useMutation(createComment, {
        onMutate: () => {
            setCreateCommentLoading(true)
        },

        onSuccess: (data) => {
            setCreateCommentLoading(false)
            setOpenDrawer(false)
            setCommentContent('')
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleCreateComment = () => {
        createCommentMutation.mutate({
            postId: post.id,
            newComment: {
                content: commentContent
            }
        })
    }

    const showCreateCommentDrawer = () => {
        setOpenDrawer(true);
    }

    // update Comment
    const updateCommentMutation = useMutation(updateComment, {
        onMutate: () => {
            setUpdateCommentLoading(true)
        },

        onSuccess: (data) => {
            setUpdateCommentLoading(false)
            setOpenDrawer(false)
            setCommentId('')
            setCommentContent('')
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleUpdateComment = () => {
        updateCommentMutation.mutate({
            postId: post.id, 
            commentId: commentId,
            newComment: {
                content: commentContent
            }
        })
    }

    const showUpdateCommentDrawer = (comment: PostComment) => {
        setOpenDrawer(true)
        setCommentId(comment.id)
        setCommentContent(comment.content)
    }

    // delete Comment

    const deleteCommentMutation = useMutation(deleteComment, {
        onMutate: () => {
            setDeleteCommetLoading(true)
        },

        onSuccess: (data) => {
            setDeleteCommetLoading(false)
            setCommentContent('')
            setOpenDrawer(false)
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleDeleteComment = (commentId: string) => {
        deleteCommentMutation.mutate(commentId)
    }

    // like Comment
    const commentLikeMutation = useMutation(likeComment, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleLikeComment = (id: string) => {
        auth ? commentLikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập")
    }

    // dislike Comment
    const commentDislikeMutation = useMutation(dislikeComment, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleDislikeComment = (id: string) => {
        auth ? commentDislikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập")
    }
    
    // common
    const closeDrawer = () => {
        setOpenDrawer(false);
        setCommentContent('')
    }

    return (
        <>
            <Divider orientation="left"><p>{sortedComment.length} Bình luận</p></Divider>
            {
                auth && (
                    <Button type="primary" onClick={showCreateCommentDrawer}><PlusOutlined /> Thêm bình luận</Button>
                )
            }
            
            <Card className="my-6">
                <List
                    size="large"
                    bordered={false}
                    className="my-8"
                    split={false}
                    itemLayout="vertical" 
                    pagination={{
                        pageSize: 5
                    }}
                    dataSource={sortedComment} 
                    renderItem={(item) => (
                        <List.Item 
                            className="mb-10 p-0"  
                            actions={[
                                <Button 
                                    className="border-none shadow-none" key="list-vertical-message"
                                    onClick={() => handleLikeComment(item.id)}
                                >
                                    <IconText
                                        icon={item.commentReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id)) ? LikeFilled : LikeOutlined} 
                                        text={item.commentReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                                    />
                                </Button>,
                                <Button 
                                    className="border-none shadow-none" key="list-vertical-message"
                                    onClick={() => handleDislikeComment(item.id)}
                                >
                                    <IconText
                                        icon={item.commentReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id)) ? DislikeFilled : DislikeOutlined} 
                                        text={item.commentReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                                    />
                                </Button>,
                                auth?.username == item.user.username ? (
                                    <Popconfirm 
                                        title="Tuỳ chọn"
                                        onConfirm={() => handleDeleteComment(item.id)}
                                        onCancel={() => showUpdateCommentDrawer(item)}
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
                                avatar={<Avatar shape="square" size={60} src={item.user?.avatar} />}
                                description={(
                                    <>
                                        <strong className="mr-2 text-sm"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                        <strong className="text-sm"><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                        <Typography className="mt-2">
                                            <div dangerouslySetInnerHTML={{ __html:item.content }} style={{wordBreak: "break-word", overflowWrap: "break-word"}} />
                                        </Typography>
                                    </>
                                )}
                            />
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
                    {commentId === '' ? (
                        <Button onClick={handleCreateComment} type="primary" loading={createCommentLoading}>
                            Thêm
                        </Button>
                    ) : (
                        <Button onClick={handleUpdateComment} type="primary" loading={updateCommentLoading}>
                            Cập nhật
                        </Button>
                    )}
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