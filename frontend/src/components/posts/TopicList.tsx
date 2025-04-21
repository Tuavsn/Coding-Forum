import React from "react";
import { getTopic } from "@/libs/service/post.service";
import { Topic } from "@/libs/constant/types";
import PostFilter from "./PostFilter";

export default async function TopicList() {

    /**
     * Fetch topic from server
     */
    const topics: Topic[] = await getTopic();

    return (
        <PostFilter availableTopics={topics} />
    )
}