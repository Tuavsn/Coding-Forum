'use client'

import { uploadButton } from "@/components/common/upload/UploadButton";
import { AuthContext } from "@/context/AuthContextProvider";
import { createPost, deletePost, dislikePost, likePost, updatePost } from "@/libs/actions/post.acttion";
import { ReactionType } from "@/libs/enum";
import { FileType, Post, PostImage, Topic } from "@/libs/types";
import { formatDate, getBase64, stringToSlug } from "@/libs/utils";
import { ClockCircleOutlined, DislikeFilled, DislikeOutlined, HeartOutlined, LikeFilled, LikeOutlined, MessageFilled, MessageOutlined, PlusOutlined, RightCircleOutlined, SettingOutlined, SmileOutlined, UploadOutlined, UserOutlined } from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, Card, Col, Divider, Drawer, Form, Input, List, message, Popconfirm, Row, Space, Typography, Upload, UploadFile, UploadProps } from "antd";
import Link from "next/link";
import React, { useContext, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import { AntdIconProps } from '@ant-design/icons/lib/components/AntdIcon';

interface postList {
    posts: Post[];
    topic: Topic;
}

const IconText = ({ icon, text }: { icon: React.ComponentType<AntdIconProps>; text: string }) => (
    <Space>
        {React.createElement(icon, {style: {fontSize: '18px'}})}
        {text}
    </Space>
);


export default function PostList({posts, topic}: postList) {
    const {auth, setAuth} = useContext(AuthContext);

    const queryClient = useQueryClient();

    const sortedPost = [...posts].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

    const [openDrawer, setOpenDrawer] = useState(false);

    const [postCreateLoading, setPostCreateLoading] = useState(false);

    const [postUpdateLoading, setPostUpdateLoading] = useState(false);

    const [postDeleteLoading, setPostDeleteLoading] = useState(false);

    const [postId, setPostId] = useState('');

    const [postHeader, setPostHeader] = useState('');

    const [postContent, setPostContent] = useState('');
    
    const [postImage, setPostImage] = useState<PostImage[]>([]);

    const [previewOpen, setPreviewOpen] = useState(false);

    const [previewImage, setPreviewImage] = useState('');

    const [fileList, setFileList] = useState<UploadFile[]>([]);

    // create Post
    const postCreateMutation = useMutation(createPost, {
        onMutate: () => {
            setPostCreateLoading(true);
        },

        onSuccess: (data) => {
            setPostCreateLoading(false);
            setOpenDrawer(false);
            queryClient.invalidateQueries('getTopic');
            setPostHeader('');
            setPostContent('');
            setPostImage([]);
            setFileList([]);
            message.success(data.Message);
        }
    })

    const handleCreatePost = () => {
        postCreateMutation.mutate({
            topicId: topic.id,
            newPost: {
                header: postHeader,
                content: postContent,
                postImage: postImage
            }
        })
    }

    const showCreatePostDrawer = () => {
        setOpenDrawer(true);
    };

    // update Post
    const postUpdateMutation = useMutation(updatePost, {
        onMutate: () => {
            setPostUpdateLoading(true);
        },

        onSuccess: (data) => {
            setPostUpdateLoading(false);
            setOpenDrawer(false);
            queryClient.invalidateQueries('getTopic');
            setPostId('');
            setPostHeader('');
            setPostContent('');
            setPostImage([]);
            setFileList([]);
            message.success(data.Message);
        }
    })

    const handleUpdatePost = () => {
        postUpdateMutation.mutate({
            postId: postId,
            newPost: {
                header: postHeader,
                content: postContent,
                postImage: postImage
            }
        })
    }

    const showUpdatePostDrawer = (post:Post) => {
        setOpenDrawer(true);
        setPostId(post.id);
        setPostHeader(post.header);
        setPostContent(post.content);
    }

    // delete Post
    const postDeleteMutation = useMutation(deletePost, {
        onMutate: () => {
            setPostDeleteLoading(true);
        },

        onSuccess: (data) => {
            setPostDeleteLoading(false);
            queryClient.invalidateQueries('getTopic');
            message.success(data.Message);
        }
    })

    const handleDeletePost = (id: string) => {
        postDeleteMutation.mutate(id);
    }

    // like Post
    const postLikeMutation = useMutation(likePost, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getTopic');
            message.success(data.Message);
        }
    })

    const handleLikePost = (id: string) => {
        auth ? postLikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập");
    }

    // dislike Post
    const postDislikeMutation = useMutation(dislikePost, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getTopic');
            message.success(data.Message);
        }
    })

    const handleDislikePost = (id: string) => {
        auth ? postDislikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập");
    }

    // common
    const closeDrawer = () => {
        setOpenDrawer(false);
        setPostHeader('');
        setPostContent('');
        setPostImage([]);
    }

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj as FileType);
        }
    
        setPreviewImage(file.url || (file.preview as string));
        setPreviewOpen(true);
    }

    const handleChange: UploadProps['onChange'] = async ({ fileList: newFileList }) => {
        const updatedFileList = await Promise.all(
            newFileList.map(async (file) => {
              if (file.originFileObj && !file.url && !file.preview) {
                const base64 = await getBase64(file.originFileObj as FileType);
                return {
                  ...file,
                  url: base64,
                }
              }
              return file;
            })
        )

        setFileList(updatedFileList);

        const postImages = updatedFileList.map((file) => ({
            image: file.url as string
        }))

        setPostImage(postImages);
    }
    
    return (
        <>
            {
                auth && (
                    <Button type="primary" onClick={showCreatePostDrawer}><PlusOutlined /> Thêm Post mới</Button>
                )
            }
            <Divider orientation="left"><p>{sortedPost.length} Bài Post</p></Divider>
            <Card>
                <List
                    size="large"
                    className="my-2"
                    bordered={false}
                    split={true}
                    itemLayout="vertical" 
                    pagination={{
                        pageSize: 10
                    }}
                    dataSource={sortedPost} 
                    renderItem={(item) => (
                        <List.Item
                            className="px-0 items-center"
                            actions={[
                                <Button 
                                    className="border-none px-2 shadow-none" key="list-vertical-message"
                                    onClick={() => handleLikePost(item.id)}
                                >
                                    <IconText
                                        icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id)) ? LikeFilled : LikeOutlined} 
                                        text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                                    />
                                </Button>,
                                <Button 
                                    className="border-none px-2 shadow-none" key="list-vertical-message"
                                    onClick={() => handleDislikePost(item.id)}
                                >
                                    <IconText 
                                        icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id)) ? DislikeFilled : DislikeOutlined} 
                                        text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                                    />
                                </Button>,
                                <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                    <IconText 
                                        icon={item.postComment.some((comment) => comment.user.id === auth?.id) ? MessageFilled : MessageOutlined} 
                                        text={item.postComment.length.toString()}
                                    />
                                </Button>,
                                auth?.username == item.user.username ? (
                                    <Popconfirm 
                                        title="Tuỳ chọn"
                                        onConfirm={() => handleDeletePost(item.id)}
                                        onCancel={() => showUpdatePostDrawer(item)}
                                        okText="Xoá"
                                        cancelText="Chỉnh sửa"
                                        key="list-vertical-message"
                                    >
                                        <Button className="border-none px-2 shadow-none"><IconText icon={SettingOutlined} text=""/></Button>
                                    </Popconfirm>
                                ) : <></>
                            ]}
                            extra={
                                item.postImage && (
                                    <img 
                                        style={{objectFit: "contain", width: "300px", height: "150px"}}
                                        alt={item.postImage[0]?.image}
                                        src={item.postImage[0]?.image}
                                    />
                                )
                            }
                        >
                            <List.Item.Meta
                                style={{overflow: "hidden"}}
                                avatar={<Avatar shape="square" size={60} src={item.user.avatar}/>}
                                title={<Link href={`/post/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                                description={(
                                    <>
                                        <strong className="mr-6"><Link href={`/user?id=${item.user.id}`}><UserOutlined /> {item.user.username}</Link></strong>
                                        <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                    </>
                                )}
                            />
                            <Typography className="relative max-h-[160px] overflow-hidden">
                                <div 
                                    dangerouslySetInnerHTML={{ __html: item.content }} 
                                    className="break-words"
                                />
                                <div className="absolute bottom-0 left-0 right-0 h-12 bg-gradient-to-t from-white to-transparent">
                                    <Link 
                                        href={`/post/${stringToSlug(item.header)}?id=${item.id}`} 
                                        className="absolute inset-0 flex items-center justify-center bg-white/80 text-gray-600 font-medium shadow-lg cursor-pointer hover:shadow-xl"
                                    >
                                        Xem thêm
                                    </Link>
                                </div>
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
                    {postId === '' ? (
                        <Button onClick={handleCreatePost} type="primary" loading={postCreateLoading}>
                            Thêm
                        </Button>
                    ) : (
                        <Button onClick={handleUpdatePost} type="primary" loading={postUpdateLoading}>
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
                                <Upload
                                    listType="picture-card"
                                    fileList={fileList}
                                    onPreview={handlePreview}
                                    onChange={handleChange}
                                    beforeUpload={() => false}
                                >
                                    {fileList.length >= 8 ? null : uploadButton}
                                </Upload>
                                {previewImage && (
                                    <img src={previewImage} width={100} alt="preview" />
                                )}
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    )
}