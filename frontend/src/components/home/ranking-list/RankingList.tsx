'use client'

import { getUserRanking } from "@/libs/actions/user.actions";
import { User } from "@/libs/types";
import { FireFilled, UserOutlined } from "@ant-design/icons";
import { List, Skeleton, Table } from "antd";
import Link from "next/link";
import { useQuery } from "react-query";

export default function RankingList() {

    const { data, isLoading } = useQuery<User[]>('getUserRanking', getUserRanking)

    console.log(data)

    if(isLoading) {
        return <Skeleton active />
    }

    return (
        <></>
        // <List
        //     size="small"
        //     className="mb-8"
        //     header={<div className="font-medium text-base"><FireFilled style={{color: "red"}} /> {dataSource.title}</div>}
        //     bordered
        //     itemLayout="vertical" 
        //     dataSource={[data]} 
        //     renderItem={(item) => (
        //         <List.Item>
        //             {/* <Table
        //                 columns={columns}
        //                 dataSource={item.tableData}
        //             /> */}
        //         </List.Item>
        //     )}
        // />
    )
}