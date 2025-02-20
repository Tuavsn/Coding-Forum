'use client'

import React from 'react';
import { Table } from 'antd';

interface CommonStatisticsProps {
  topicCount: number;
  postCount: number;
  userCount: number;
  reactionCount: number;
  commentCount: number;
}

export default function CommonStatistics(props: CommonStatisticsProps) {
  const { topicCount, postCount, userCount, reactionCount, commentCount } = props;

  const data = [
    { key: '1', category: 'Chủ đề', count: topicCount },
    { key: '2', category: 'Bài đăng', count: postCount },
    { key: '3', category: 'Tài khoản', count: userCount },
    { key: '4', category: 'Cảm xúc', count: reactionCount },
    { key: '5', category: 'Bình luận', count: commentCount },
  ];

  const columns = [
    {
      title: 'Danh mục',
      dataIndex: 'category',
      key: 'category',
    },
    {
      title: 'Số lượng',
      dataIndex: 'count',
      key: 'count',
      render: (text: number) => <span className="text-lg font-bold">{text}</span>,
    },
  ];

  return (
    <Table
      columns={columns}
      dataSource={data}
      pagination={false}
      bordered
      className="text-center shadow-md rounded-lg"
    />
  );
}
