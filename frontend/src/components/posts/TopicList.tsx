import React from "react";
import { getTopic } from "@/libs/actions/post.acttion";
import { Topic } from "@/libs/types";
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