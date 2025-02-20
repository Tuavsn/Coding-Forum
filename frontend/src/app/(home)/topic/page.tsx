// File: pages/TopicList.tsx
import List from "@/components/common/List";
import { getTopic } from "@/libs/actions/post.acttion";
import React from "react";

interface Topic {
  id: number;
  title: string;
  description: string;
  createdAt: string;
}

const topics: Topic[] = [
  {
    id: 1,
    title: "React Basics",
    description: "Learn the fundamentals of React, including components, props, and state.",
    createdAt: "2025-01-20",
  },
  {
    id: 2,
    title: "Next.js Server-Side Rendering",
    description: "A deep dive into SSR and how it works with Next.js.",
    createdAt: "2025-01-18",
  },
  {
    id: 3,
    title: "TypeScript Essentials",
    description: "An introduction to TypeScript and its features for JavaScript developers.",
    createdAt: "2025-01-15",
  },
];

export default async function TopicPage() {

  /**
     * Fetch topic from server
     */
  const topics = await getTopic();

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-6">Topics List</h1>
      
      <List
        dataSource={topics}
        renderItem={(topic: Topic, index: number) => (
          <div>
            <h2 className="text-xl font-semibold">{topic.title}</h2>
            <p className="text-gray-600">{topic.description}</p>
            <p className="text-sm text-gray-400">Created At: {topic.createdAt}</p>
          </div>
        )}
        header={<div className="text-lg font-medium">Available Topics</div>}
        footer={<div className="text-sm text-gray-500">End of topics list</div>}
        split={true}
        bordered={true}
        itemLayout="vertical"
        className="bg-white shadow rounded-lg"
        styles={{ padding: "1rem" }}
      />
    </div>
  );
}
