'use client'

import { FireFilled, UserOutlined } from "@ant-design/icons";
import { List, Table } from "antd";
import Link from "next/link";

const dataSource =
{
    title: "Bảng xếp hạng",
    tableData: [
        {
            key: '1',
            user: 'user 1',
            totalScore: '10',
            link: (
                <Link href="/user"><UserOutlined /> Profile</Link>
            )
        },
        {
            key: '2',
            user: 'user 2',
            totalScore: '9',
            link: (
                <Link href="/user"><UserOutlined /> Profile</Link>
            )
        },
        {
            key: '3',
            user: 'user 3',
            totalScore: '8',
            link: (
                <Link href="/user"><UserOutlined /> Profile</Link>
            )
        },
        {
            key: '4',
            user: 'user 4',
            totalScore: '7',
            link: (
                <Link href="/user"><UserOutlined /> Profile</Link>
            )
        },
    ]
}
  
  const columns = [
    {
        title: 'Tài khoản',
        dataIndex: 'user',
        key: 'user',
    },
    {
        title: 'Tổng số điểm',
        dataIndex: 'totalScore',
        key: 'totalScore',
    },
    {
        title: 'Thông tin tài khoản',
        dataIndex: 'link',
        key: 'link',
    },
  ];


export default function RankingList() {
    return (
        <List
            size="small"
            className="mb-8"
            header={<div className="font-medium text-base"><FireFilled style={{color: "red"}} /> {dataSource.title}</div>}
            bordered
            itemLayout="vertical" 
            dataSource={[dataSource]} 
            renderItem={(item) => (
                <List.Item>
                    <Table
                        columns={columns}
                        dataSource={item.tableData}
                    />
                </List.Item>
            )}
        />
    )
}