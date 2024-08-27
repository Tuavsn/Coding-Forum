'use client'

import { Tabs } from "antd";
import React from "react";
import PostList from "../post-list/PostList";
import { useQuery } from "react-query";
import { getTopic } from "@/libs/actions/post.acttion";

export default function TopicList() {

    const { data } = useQuery<Topic[]>('getTopic', getTopic)

    return (
        <Tabs defaultActiveKey="0" items={
            data?.map((topic, index) => ({
            key: topic.id,
            label: topic.name,
            children: (
                <PostList posts={topic.posts} topic={topic} />
            )
            })
        )}
        >
        </Tabs>
    )
}