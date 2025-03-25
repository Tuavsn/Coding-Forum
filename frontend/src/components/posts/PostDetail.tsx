import { AuthContext } from "@/context/AuthContextProvider";
import { getPostDetail } from "@/libs/actions/post.acttion";
import { Post } from "@/libs/types";
import { formatDate } from "@/libs/utils";
import { 
    ClockCircleOutlined, 
} from "@ant-design/icons";
import { Avatar, Card, Carousel, Divider, Space, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import React, { useContext, useEffect } from "react";
import { AntdIconProps } from '@ant-design/icons/lib/components/AntdIcon';
import Comment from "../common/Comment";
import PostModal from "./PostModal";
import usePost from "@/hooks/usePost";
import PostAction from "./PostAction";

const IconText = ({ icon, text }: { icon: React.ComponentType<AntdIconProps>; text: string }) => (
    <Space>
        {React.createElement(icon, {style: {fontSize: '18px'}})}
        {text}
    </Space>
);

interface Props {
    postId: string | string[] | undefined;
}

export default async function PostDetail(props: Props) {

    const { postId } = props;

    const post: Post = await getPostDetail(postId ? postId as string : '')

    return (
        post && (
            <>
                <Card
                    className="w-full"
                    actions={[
                        <PostAction
                            post={post}
                            allowLike={true}
                            allowDislike={true}
                            allowEdit={true}
                            allowDelete={true}
                        />
                    ]}
                    // actions={[
                    //     <Button 
                    //         className="border-none px-2 shadow-none" key="list-vertical-message"
                    //         onClick={() => handleLikePost(data.id)}
                    //     >
                    //         <IconText 
                    //             icon={data.postReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id)) ? LikeFilled : LikeOutlined} 
                    //             text={data.postReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                    //         />
                    //     </Button>,
                    //     <Button 
                    //         className="border-none px-2 shadow-none" key="list-vertical-message"
                    //         onClick={() => handleDislikePost(data.id)}
                    //     >
                    //         <IconText 
                    //             icon={data.postReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id)) ? DislikeFilled : DislikeOutlined} 
                    //             text={data.postReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                    //         />
                    //     </Button>,
                    //     <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                    //         <IconText 
                    //             icon={data.postComment.some((comment) => comment.user.id === auth?.id) ? MessageFilled : MessageOutlined} 
                    //             text={data.postComment.length.toString()}
                    //         />
                    //     </Button>,
                    //     auth?.username == data.user.username ? (
                    //         <Popconfirm
                    //             title="Tuỳ chọn"
                    //             onConfirm={() => handleDeletePost(postId ? postId : '')}
                    //             onCancel={() => showUpdatePostDrawer(data)}
                    //             okText="Xoá"
                    //             cancelText="Chỉnh sửa"
                    //             key="list-vertical-message"
                    //         >
                    //             <Button className="border-none px-2 shadow-none"><IconText icon={SettingOutlined} text=""/></Button>
                    //         </Popconfirm>
                    //     ) : <></>
                    // ]}
                >
                    <Meta
                        avatar={<Avatar shape="square" size={60} src={post.user.avatar} />}
                        title={
                            <div>
                                <p className="text-xl">{post.header}</p>
                                <p className="text-sm text-slate-400 mt-1">
                                    <Link href={`/user?id=${post.user.id}`} className="text-blue-500 hover:underline">
                                        {post.user.username}
                                    </Link>
                                    <ClockCircleOutlined className="ml-4" /> {formatDate(post.createdAt.toString())}
                                </p>
                            </div>
                        }
                    />
                    <Divider />
                    {post.postImage && (
                        <Carousel adaptiveHeight={true}>
                            {post.postImage.map((image, index) => (
                                <div key={index} className="">
                                    <img src={image.image} alt={image.image} className="h-[500px] mx-auto object-contain"/>
                                </div>
                            ))}
                        </Carousel>
                    )}
                    <Typography className="mt-2">
                        <div dangerouslySetInnerHTML={{ __html:post.content }} className="ck-content break-words whitespace-pre-wrap" />
                    </Typography>
                </Card>
                <Comment post={post}/>
            </>
        )
    )
}