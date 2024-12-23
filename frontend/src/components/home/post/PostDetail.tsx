'use client'

import Comment from "@/components/common/comment/Comment";
import Loading from "@/components/common/loading/Loading";
import { uploadButton } from "@/components/common/upload/UploadButton";
import { AuthContext } from "@/context/AuthContextProvider";
import { deletePost, dislikePost, getPostDetail, likePost, updatePost } from "@/libs/actions/post.acttion";
import { ReactionType } from "@/libs/enum";
import { FileType, Post, PostImage } from "@/libs/types";
import { formatDate, getBase64 } from "@/libs/utils";
import { 
    ClockCircleOutlined, 
    DislikeFilled, 
    DislikeOutlined, 
    HeartOutlined, 
    LikeFilled, 
    LikeOutlined, 
    MessageFilled, 
    MessageOutlined, 
    SettingOutlined, 
    SmileOutlined, 
    UploadOutlined, 
    UserOutlined 
} from "@ant-design/icons";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { Avatar, Button, Card, Carousel, Col, Divider, Drawer, Form, Input, message, Popconfirm, Row, Skeleton, Space, Typography, Upload, UploadFile, UploadProps } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import React, { useContext, useEffect, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { AntdIconProps } from '@ant-design/icons/lib/components/AntdIcon';

const IconText = ({ icon, text }: { icon: React.ComponentType<AntdIconProps>; text: string }) => (
    <Space>
        {React.createElement(icon, {style: {fontSize: '18px'}})}
        {text}
    </Space>
);

export default function PostDetail() {
    const {auth, setAuth} = useContext(AuthContext);

    const queryClient = useQueryClient();
    
    const postId = useSearchParams().get('id');

    const router = useRouter();

    const { data, isLoading } = useQuery<Post>(
        ['getPostDetail', postId], () => getPostDetail(postId),
    );

    const [openDrawer, setOpenDrawer] = useState(false);

    const [postUpdateLoading, setPostUpdateLoading] = useState(false);

    const [postDeleteLoading, setPostDeleteLoading] = useState(false);

    const [postHeader, setPostHeader] = useState('');

    const [postContent, setPostContent] = useState('');

    const [postImage, setPostImage] = useState<PostImage[]>([]);

    const [previewOpen, setPreviewOpen] = useState(false);

    const [previewImage, setPreviewImage] = useState('');

    const [fileList, setFileList] = useState<UploadFile[]>([]);

    const [form] = Form.useForm();

    // update Post
    const postUpdateMutation = useMutation(updatePost, {
        onMutate: () => {
            setPostUpdateLoading(true)
        },

        onSuccess: (data) => {
            setPostUpdateLoading(false)
            setOpenDrawer(false)
            queryClient.invalidateQueries('getPostDetail')
            setPostHeader('')
            setPostContent('')
            setPostImage([])
            setFileList([])
            message.success(data.Message)
        }
    })

    const handleUpdatePost = async () => {
        try {
            await form.validateFields();
            postUpdateMutation.mutate({
                postId: postId ? postId: '',
                newPost: {
                    header: postHeader,
                    content: postContent,
                    postImage: postImage
                }
            })
        } catch (error: any) {
            message.error(error);
        }
    }

    const showUpdatePostDrawer = (post:Post) => {
        setOpenDrawer(true);
        setPostHeader(post.header)
        setPostContent(post.content)
    }

    // delete Post
    const postDeleteMutation = useMutation(deletePost, {
        onMutate: () => {
            setPostDeleteLoading(true)
        },

        onSuccess: (data) => {
            setPostDeleteLoading(false)
            queryClient.invalidateQueries('getTopic')
            router.push("/home")
            message.success(data.Message)
        }
    })

    const handleDeletePost = (postId: string) => {
        postDeleteMutation.mutate(postId)
    }

    // like Post
    const postLikeMutation = useMutation(likePost, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleLikePost = (id: string) => {
        auth ? postLikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập")
    }

    // dislike Post
    const postDislikeMutation = useMutation(dislikePost, {
        onSuccess: (data) => {
            queryClient.invalidateQueries('getPostDetail')
            message.success(data.Message)
        }
    })

    const handleDislikePost = (id: string) => {
        auth ? postDislikeMutation.mutate(id) : message.error("Bạn chưa đăng nhập")
    }

    // common
    const closeDrawer = () => {
        setOpenDrawer(false);
        setPostHeader('')
        setPostContent('')
        setPostImage([])
    };

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj as FileType)
        }
    
        setPreviewImage(file.url || (file.preview as string))
        setPreviewOpen(true)
    }

    const handleChange: UploadProps['onChange'] = async ({ fileList: newFileList }) => {
        const updatedFileList = await Promise.all(
            newFileList.map(async (file) => {
              if (file.originFileObj && !file.url && !file.preview) {
                const base64 = await getBase64(file.originFileObj as FileType)
                return {
                  ...file,
                  url: base64,
                }
              }
              return file
            })
        )

        setFileList(updatedFileList)

        const postImages = updatedFileList.map((file) => ({
            image: file.url as string
        }))

        setPostImage(postImages);
    }

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [postId]);

    if(isLoading) {
        return <Skeleton active />
    }

    return (
        data && (
            <>
                <Card
                    className="w-full"
                    actions={[
                        <Button 
                            className="border-none px-2 shadow-none" key="list-vertical-message"
                            onClick={() => handleLikePost(data.id)}
                        >
                            <IconText 
                                icon={data.postReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id)) ? LikeFilled : LikeOutlined} 
                                text={data.postReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                            />
                        </Button>,
                        <Button 
                            className="border-none px-2 shadow-none" key="list-vertical-message"
                            onClick={() => handleDislikePost(data.id)}
                        >
                            <IconText 
                                icon={data.postReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id)) ? DislikeFilled : DislikeOutlined} 
                                text={data.postReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                            />
                        </Button>,
                        <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                            <IconText 
                                icon={data.postComment.some((comment) => comment.user.id === auth?.id) ? MessageFilled : MessageOutlined} 
                                text={data.postComment.length.toString()}
                            />
                        </Button>,
                        auth?.username == data.user.username ? (
                            <Popconfirm
                                title="Tuỳ chọn"
                                onConfirm={() => handleDeletePost(postId ? postId : '')}
                                onCancel={() => showUpdatePostDrawer(data)}
                                okText="Xoá"
                                cancelText="Chỉnh sửa"
                                key="list-vertical-message"
                            >
                                <Button className="border-none px-2 shadow-none"><IconText icon={SettingOutlined} text=""/></Button>
                            </Popconfirm>
                        ) : <></>
                    ]}
                >
                    <Meta
                        avatar={<Avatar shape="square" size={60} src={data.user.avatar} />}
                        title={
                            <div>
                                <p className="text-xl">{data.header}</p>
                                <p className="text-sm text-slate-400 mt-1">
                                    <Link href={`/profile/${data.user.id}`} className="text-blue-500 hover:underline">
                                        {data.user.username}
                                    </Link>
                                    <ClockCircleOutlined className="ml-4" /> {formatDate(data.createdAt.toString())}
                                </p>
                            </div>
                        }
                    />
                    <Divider />
                    {data.postImage && (
                        <Carousel adaptiveHeight={true}>
                            {data.postImage.map((image, index) => (
                                <div key={index} className="">
                                    <img src={image.image} alt={image.image} className="h-[500px] mx-auto object-contain"/>
                                </div>
                            ))}
                        </Carousel>
                    )}
                    <Typography className="mt-2">
                        <div dangerouslySetInnerHTML={{ __html:data.content }} style={{wordBreak: "break-word", overflowWrap: "break-word"}} />
                    </Typography>
                </Card>
                <Comment post={data}/>

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
                        <Button onClick={handleUpdatePost} type="primary" loading={postUpdateLoading}>
                            Cập nhật
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
                                        <img src={previewImage} alt="preview"/>
                                    )}
                                </Form.Item>
                            </Col>
                        </Row>
                    </Form>
                </Drawer>
            </>
        )
    )
}