import { Card, Divider, Tabs } from "antd";
import React from "react";
import { getTopic } from "@/libs/actions/post.acttion";
import PostList from "./PostList";
import List from "../common/List";
import { Topic } from "@/libs/types";
import { formatDate } from "@/libs/utils";
import PostFilter from "./PostFilter";
import CommonStatistics from "./CommonStatistics";

export default async function TopicList() {

    /**
     * Fetch topic from server
     */
    const topics: Topic[] = await getTopic();

    return (
        <>
            <div className="grid grid-cols-4 gap-4">
                <div className="col-span-3">
                    <PostList posts={topics[0].posts.toSpliced(4)} />
                </div>
                <div>
                    <Divider orientation="left"><p>Bộ lọc</p></Divider>
                    <PostFilter availableTopics={topics} />
                    <Divider orientation="left"><p>Thống kê</p></Divider>
                    {/* <CommonStatistics
                        topicCount={topics.length}
                        postCount={topics[0].posts.length}
                        userCount={topics[0].posts[0].user.username.length}
                        reactionCount={topics[0].posts[0].postReactions.length}
                        commentCount={topics[0].posts[0].postComment.length}
                    /> */}
                </div>
            </div>
        </>
    )
}