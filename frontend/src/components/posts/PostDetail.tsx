import { getPostDetail } from "@/libs/service/post.service";
import { Post } from "@/libs/constant/types";
import { formatDate } from "@/libs/utils";
import { 
    ClockCircleOutlined, 
} from "@ant-design/icons";
import { Avatar, Card, Carousel, Divider, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import Comment from "../common/Comment";
import PostAction from "./PostAction";
import React from "react";

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
                            key="list-vertical-action-bar"
                            post={post}
                            allowLike={true}
                            allowDislike={true}
                            allowEdit={true}
                            allowDelete={true}
                        />
                    ]}
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