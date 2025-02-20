import { Post } from "@/libs/types";
import { formatDate, stringToSlug } from "@/libs/utils";
import { ClockCircleOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Card, Divider, Typography } from "antd";
import Link from "next/link";
import React from "react";
import List from "../common/List";
import PostAction from "./PostAction";

interface postList {
    posts: Post[];
}

export default function PostList({posts}: postList) {

    const sortedPost = [...posts].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
    
    return (
        <>
            <Divider orientation="left"><p>{sortedPost.length} Bài Post</p></Divider>
            <Card className="shadow-md">
                <List
                    split={true}
                    bordered={true}
                    itemLayout="vertical"
                    dataSource={sortedPost}
                    renderItem={(item: Post) => (
                        <div className="flex items-center gap-4">
                            <div className="flex-1 p-2 min-w-0">
                                <div className="flex items-center gap-4">
                                    <Avatar shape="square" size={60} src={item.user.avatar} />
                                    <div>
                                        <Link href={`/post/${stringToSlug(item.header)}?id=${item.id}`}>
                                            <h3 className="font-bold text-lg">{item.header}</h3>
                                        </Link>
                                        <div className="flex items-center text-gray-500 text-sm">
                                            <Link href={`/user?id=${item.user.id}`}>
                                                <strong className="mr-4">
                                                    <UserOutlined /> {item.user.username}
                                                </strong>
                                            </Link>
                                            <strong>
                                                <ClockCircleOutlined /> {formatDate(item.createdAt.toString())}
                                            </strong>
                                        </div>
                                    </div>
                                </div>
                                <Typography className="relative max-h-[160px] overflow-hidden mt-2">
                                    <div
                                        dangerouslySetInnerHTML={{ __html: item.content }}
                                        className="ck-content break-words whitespace-pre-wrap"
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
                                {/* Post Action (like, dislike, comment, edit, delete, ...) */}
                                <PostAction
                                    post={item}
                                    allowLike={true}
                                    allowDislike={true}
                                    allowComment={true}
                                    allowEdit={true}
                                    allowDelete={true}
                                />
                            </div>
                            {item.postImage && (
                                <img
                                    style={{ objectFit: "contain", width: "300px", height: "150px" }}
                                    alt={item.postImage[0]?.image}
                                    src={item.postImage[0]?.image}
                                />
                            )}
                        </div>
                    )}
                />
            </Card>
        </>
    )
}