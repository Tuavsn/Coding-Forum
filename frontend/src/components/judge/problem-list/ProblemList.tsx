'use client'

import { Card, List, Space, Tabs, Tag } from "antd";
import TabPane from "antd/es/tabs/TabPane";
import React from "react";
import { PlayCircleOutlined, MessageOutlined, ClockCircleOutlined } from "@ant-design/icons";
import Link from "next/link";

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

function getTopicColor(str: string): string {
    switch(str) {
        case "Dễ":
            return "#87d068"
        case "Trung bình":
            return "#108ee9"
        default:
            return "#f50"
    }
}

const dataSource = [
    {
        name: "Dễ",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Tư vấn tuyển sinh, chọn ngành nghề, review trường học tất cả cho vào đây',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Leetcode mỗi ngày',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Top công ty công nghệ cho lập trình viên ở VN',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: '[Java] Thắc mắc về Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    },
    {
        name: "Trung bình",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Word',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Excel',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'PowerPoint',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Zalo',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    },
    {
        name: "Khó",
        posts: [
            {
                id: '1',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C++',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '2',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Java',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '3',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'C#',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
            {
                id: '4',
                user: {
                    avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
                },
                header: 'Javascript',
                totalComment: '10',
                description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
                postImage: [
                    "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                ],
                createAt: '19/8/2024 10:23 am'
            },
        ]
    }
];

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function ProblemList() {
    return (
        <Tabs defaultActiveKey="0" items={dataSource.map((topic, index) => ({
            key: index.toString(),
            label: topic.name,
            children: (
                <Card>
                    <List
                        size="large"
                        className="mb-8"
                        bordered={false}
                        split={false}
                        itemLayout="vertical" 
                        pagination={{
                            pageSize: 10
                        }}
                        dataSource={topic.posts} 
                        renderItem={(item) => (
                            <List.Item
                                className="px-0"
                                actions={[
                                    <Tag key='1' color={getTopicColor(topic.name)}>{topic.name}</Tag>,
                                    <IconText icon={PlayCircleOutlined} text="232" key="list-vertical-message" />,
                                    <IconText icon={MessageOutlined} text="244" key="list-vertical-message" />
                                ]}
                            >
                                <List.Item.Meta
                                    title={<Link href={`/problem/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                                    description={<strong><ClockCircleOutlined /> {item.createAt}</strong>}
                                />
                                {item.description}
                            </List.Item>
                        )}
                    />
                </Card>
            )
        }))}>
        </Tabs>
    )
}