'use client'

import { 
    ClockCircleOutlined,
    DislikeOutlined,
    HeartOutlined,
    LikeOutlined,
    PlusOutlined,
    SmileOutlined,
    UserOutlined 
} from "@ant-design/icons";
import { Avatar, Input, Button, List, Space, Form, Upload, Card, Divider } from "antd";
import Link from "next/link";
import React, { useState } from "react";

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);


const exampleData = {
    comment: [
        {
            user: {
                username: "admin1",
                avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
            },
            content: "Nice content",
            createAt: '19/8/2024 10:23 am'
        },
        {
            user: {
                username: "admin1",
                avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
            },
            content: "Nice content",
            createAt: '19/8/2024 10:23 am'
        },
        {
            user: {
                username: "admin1",
                avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
            },
            content: "Nice content",
            createAt: '19/8/2024 10:23 am'
        },
        {
            user: {
                username: "admin1",
                avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
            },
            content: "Nice content",
            createAt: '19/8/2024 10:23 am'
        },
    ]
}

export default function Comment() {
    const [loading, setLoading] = useState(false)

    const handleSetLoading = () => {
        setLoading(true)

        setTimeout(() => {
            setLoading(false)
        }, 3000);
    }

    return (
        <>
            <Divider orientation="left"><p>{exampleData.comment.length} Bình luận</p></Divider>
            <Button type="primary" loading={loading} onClick={handleSetLoading}><PlusOutlined /> Thêm bình luận</Button>
            <List
                size="large"
                className="my-6"
                bordered={false}
                split={false}
                itemLayout="vertical" 
                pagination={{
                    pageSize: 5
                }}
                dataSource={exampleData.comment} 
                renderItem={(item) => (
                    <List.Item className="my-16 p-0"  
                        actions={[
                            <IconText icon={HeartOutlined} text="222" key="list-vertical-message" />,
                            <IconText icon={SmileOutlined} text="242" key="list-vertical-message" />,
                            <IconText icon={LikeOutlined} text="333" key="list-vertical-message" />,
                            <IconText icon={DislikeOutlined} text="232" key="list-vertical-message" />,
                        ]}
                    >
                        <List.Item.Meta
                            avatar={<Avatar shape="square" size={60} src={item.user.avatar} />}
                            description={(
                                <>
                                    <strong className="mr-2 text-sm"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                    <strong className="text-sm"><ClockCircleOutlined /> {item.createAt}</strong>
                                    <p className="text-black">{item.content}</p>
                                </>
                            )}
                        />
                    </List.Item>
                )}
            />
        </>
    )
}