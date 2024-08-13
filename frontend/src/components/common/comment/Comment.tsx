'use client'

import { ClockCircleOutlined, DislikeOutlined, HeartOutlined, LikeOutlined, PlusOutlined, SendOutlined, SmileOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Input, Button, List, Space, Form, Upload, Card } from "antd";
import TextArea from "antd/es/input/TextArea";
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
            <Form 
                layout="vertical"
            >
                <Form.Item label="Ảnh" valuePropName="file">
                    <Upload action="/upload.do" listType="picture-card">
                        <button style={{ border: 0, background: 'none' }} type="button">
                            <PlusOutlined />
                            <div style={{ marginTop: 8 }}>Upload</div>
                        </button>
                    </Upload>
                </Form.Item>
                <Form.Item label="Nội dung">
                    <TextArea rows={5} cols={4}/>
                </Form.Item>
                <Button size="large" type="primary" loading={loading} onClick={handleSetLoading}><SendOutlined /> Gửi bình luận</Button>
            </Form>
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
                    <List.Item style={{padding: 0}}>
                        <Card
                            className="my-4"
                            actions={[
                                <IconText icon={HeartOutlined} text="222" key="list-vertical-message" />,
                                <IconText icon={SmileOutlined} text="242" key="list-vertical-message" />,
                                <IconText icon={LikeOutlined} text="333" key="list-vertical-message" />,
                                <IconText icon={DislikeOutlined} text="232" key="list-vertical-message" />,
                            ]}
                        >
                            <List.Item.Meta
                                avatar={<Avatar shape="square" size={70} src={item.user.avatar} />}
                                description={(
                                    <>
                                        <strong className="mr-2 text-sm"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                        <strong className="text-sm"><ClockCircleOutlined /> {item.createAt}</strong>
                                        <p className="text-base text-black">{item.content}</p>
                                    </>
                                )}
                            />
                        </Card>
                    </List.Item>
                )}
            />
        </>
    )
}