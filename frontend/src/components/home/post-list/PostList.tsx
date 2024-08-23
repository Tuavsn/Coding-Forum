'use client'

import { formatDate, stringToSlug, truncateWords } from "@/libs/utils";
import { ClockCircleOutlined, DislikeOutlined, HeartOutlined, LikeOutlined, MessageOutlined, RightCircleOutlined, SmileOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Image, List, Space } from "antd";
import Link from "next/link";
import React from "react";

interface postList {
    posts: Post[];
    topicSlug: string;
}

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);


export default function PostList({posts, topicSlug}: postList) {
    return (
        <List
            size="large"
            className="mb-8"
            bordered
            split={false}
            itemLayout="vertical" 
            pagination={{
                pageSize: 10
            }}
            dataSource={posts} 
            renderItem={(item) => (
                <List.Item
                    actions={[
                        <IconText icon={HeartOutlined} text="222" key="list-vertical-message" />,
                        <IconText icon={SmileOutlined} text="242" key="list-vertical-message" />,
                        <IconText icon={LikeOutlined} text="333" key="list-vertical-message" />,
                        <IconText icon={DislikeOutlined} text="232" key="list-vertical-message" />,
                        <IconText icon={MessageOutlined} text="244" key="list-vertical-message" />
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
                        title={<Link href={`/post/${topicSlug}/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                        description={(
                            <>
                                <strong className="mr-6"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                            </>
                        )}
                    />
                    {truncateWords(item.content, 100)}
                </List.Item>
            )}
        />
    )
}