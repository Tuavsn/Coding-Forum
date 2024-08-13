'use client'

import { 
    ClockCircleOutlined, 
    DislikeOutlined, 
    HeartOutlined, 
    LikeOutlined, 
    MessageOutlined, 
    SmileOutlined, 
    UserOutlined 
} from "@ant-design/icons";
import { Avatar, Card, Carousel, Divider, Image, Space, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import React from "react";

interface User {
    username: string;
    avatar: string;
}

interface Post {
    id: string;
    user: User;
    header: string;
    totalComment: string;
    description: string;
    postImage: string[];
    createAt: string;
}

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function PostDetail({post}: {post: Post}) {
    return (
        <Card
            className="w-full"
            actions={[
                <IconText icon={HeartOutlined} text="222" key="list-vertical-message" />,
                <IconText icon={SmileOutlined} text="242" key="list-vertical-message" />,
                <IconText icon={LikeOutlined} text="333" key="list-vertical-message" />,
                <IconText icon={DislikeOutlined} text="232" key="list-vertical-message" />,
                <IconText icon={MessageOutlined} text="244" key="list-vertical-message" />
            ]}
        >
            <Meta
                avatar={<Avatar shape="square" size={60} src={post.user.avatar} />}
                title={
                    <div>
                        <p className="text-2xl">{post.header}</p>
                        <p className="text-base text-slate-400 mt-1">
                            <Link href="/user"><UserOutlined /> {post.user.username}</Link>
                            <ClockCircleOutlined className="ml-4" /> {post.createAt}
                        </p>
                    </div>
                }
            />
            <Divider />
            {post.postImage && (
                <Carousel arrows style={{backgroundColor: "black"}}>
                    {post.postImage.map((image, index) => (
                        <Image key={index} src={image} alt={image} />
                    ))}
                </Carousel>
            )}
            <Typography className="mt-2 text-base">
                {post.description}
            </Typography>
        </Card>
    )
}