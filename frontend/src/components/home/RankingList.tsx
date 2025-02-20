'use client';

import { getUserRanking } from "@/libs/actions/user.actions";
import { User } from "@/libs/types";
import { FireFilled, UserOutlined, TrophyOutlined, SafetyCertificateOutlined, SlidersOutlined, FireOutlined, CrownOutlined } from "@ant-design/icons";
import { List, Skeleton, Avatar, Tag, Pagination, Flex } from "antd";
import Link from "next/link";
import { useQuery } from "react-query";
import { useState } from "react";

export default function RankingList() {
    const { data, isLoading } = useQuery<User[]>('getUserRanking', getUserRanking);

    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 20;

    if (isLoading) {
        return <Skeleton active />;
    }

    if (!data || data.length === 0) {
        return <div className="text-center mt-4 text-gray-500">No users found.</div>;
    }

    const paginatedData = data.slice((currentPage - 1) * pageSize, currentPage * pageSize);

    // Hàm để xác định màu sắc của Trophy dựa trên thứ hạng
    const getRankColor = (rank: number) => {
        if (rank === 1) return 'gold';
        if (rank === 2) return 'silver';
        if (rank === 3) return 'bronze';
        return 'gray';
    };

    return (
        <div className="p-4 bg-white shadow-md rounded-md">
            <List
                size="small"
                className="mb-4"
                header={
                    <div className="font-medium text-lg flex items-center space-x-2">
                        <FireFilled style={{ color: "red" }} />
                        <span>User Rankings</span>
                    </div>
                }
                itemLayout="horizontal"
                dataSource={paginatedData}
                renderItem={(user, index) => (
                    <Flex>
                        <List.Item className="hover:bg-gray-50 p-4 rounded-md transition duration-200 ease-in-out grow">
                            <List.Item.Meta
                                avatar={
                                    <Avatar
                                        src={user.avatar}
                                        icon={!user.avatar && <UserOutlined />}
                                        className="bg-gray-200"
                                        size={80}
                                    />
                                }
                                title={
                                    <Link href={`/user?id=${user.id}`} className="text-blue-500 hover:underline">
                                        <strong>{user.username}</strong>
                                    </Link>
                                }
                                description={
                                    <div className="space-y-2 text-sm text-gray-600">
                                        <div className="flex items-center">
                                            <TrophyOutlined className="mr-1 text-yellow-500" />
                                            <span className="font-medium">Danh hiệu:</span>
                                            <Tag color="green" className="ml-2">{user.achievement}</Tag>
                                        </div>
                                        <div className="flex items-center">
                                            <SlidersOutlined className="mr-1 text-yellow-500" />
                                            <span className="font-medium">Trạng thái:</span>
                                            <Tag color={user.status === 'ACTIVE' ? 'green' : 'volcano'} className="ml-2">
                                                {user.status}
                                            </Tag>
                                        </div>
                                        <div className="flex items-center">
                                            <FireOutlined className="mr-1 text-yellow-500" />
                                            <span className="font-medium">Tổng điểm:</span>
                                            <Tag color="purple" className="ml-2">
                                                {user.totalSubmissionPoint}
                                            </Tag>
                                        </div>
                                    </div>
                                }
                            />
                        </List.Item>
                        <div className="flex items-center">
                            <CrownOutlined
                                style={{ fontSize: '4rem', color: getRankColor(index + 1) }}
                            />
                            <span className="font-medium">Rank:</span>
                            <Tag color={getRankColor(index + 1)} className="ml-2">
                                #{index + 1}
                            </Tag>
                        </div>
                    </Flex>
                )}
            />
            <Pagination
                className="mt-4 text-center"
                current={currentPage}
                pageSize={pageSize}
                total={data.length}
                onChange={(page) => setCurrentPage(page)}
                showSizeChanger={false}
            />
        </div>
    );
}
