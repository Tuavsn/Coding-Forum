'use client'

import { ClockCircleOutlined, DislikeOutlined, HeartOutlined, LikeOutlined, MessageOutlined, RightCircleOutlined, SmileOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Image, List, Space } from "antd";
import Link from "next/link";
import React from "react";

function stringToSlug(str: string) {
   // Chuyển tất cả các ký tự thành chữ thường
   str = str.toLowerCase();

   // Thay thế các ký tự đặc biệt tiếng Việt
   str = str.replace(/đ/g, 'd');

   // Loại bỏ dấu tiếng Việt
   str = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");

   // Thay thế các ký tự không phải chữ cái hoặc số bằng dấu gạch ngang
   str = str.replace(/[^a-z0-9\s-]/g, '');

   // Thay thế khoảng trắng hoặc dấu gạch ngang liên tiếp bằng một dấu gạch ngang
   str = str.trim().replace(/\s+/g, '-').replace(/-+/g, '-');

   return str;
}

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
                        <Image 
                            width={300}
                            alt="logo"
                            src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png" 
                        />
                    }
                >
                    <List.Item.Meta
                        avatar={<Avatar shape="square" size={60} src={item.user.avatar} />}
                        title={<Link href={`/post/${topicSlug}/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                        description={(
                            <>
                                <strong className="mr-6"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                <strong><ClockCircleOutlined /> {item.createAt}</strong>
                            </>
                        )}
                    />
                    {item.description}
                </List.Item>
            )}
        />
    )
}