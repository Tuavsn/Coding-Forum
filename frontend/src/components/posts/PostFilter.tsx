'use client'

import React, { useState } from "react";
import { Input, Select, Calendar, Button, Tag } from "antd";
import dayjs, { Dayjs } from "dayjs";

const { Option } = Select;
const { CheckableTag } = Tag;

interface Topic {
  id: string;
  name: string;
}

interface Filters {
  keyword: string;
  category: string;
  date: string | null;
  topics: string[];
}

interface PostFilterProps {
  onFilter?: (filters: Filters) => void;
  availableTopics: Topic[];
}

const PostFilter: React.FC<PostFilterProps> = ({ onFilter, availableTopics }) => {
  const [filters, setFilters] = useState<Filters>({
    keyword: "",
    category: "",
    date: null,
    topics: [],
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilters({ ...filters, keyword: e.target.value });
  };

  const handleCategoryChange = (value: string) => {
    setFilters({ ...filters, category: value });
  };

  const handleDateSelect = (date: Dayjs) => {
    setFilters({ ...filters, date: date ? date.format("YYYY-MM-DD") : null });
  };

  const handleTopicChange = (topicId: string, checked: boolean) => {
    const nextSelectedTopics = checked
      ? [...filters.topics, topicId]
      : filters.topics.filter((id) => id !== topicId);
    setFilters({ ...filters, topics: nextSelectedTopics });
  };

  const handleFilter = () => {
    if (onFilter) {
      onFilter(filters);
    }
  };

  const handleReset = () => {
    setFilters({ keyword: "", category: "", date: null, topics: [] });
    if (onFilter) {
      onFilter({ keyword: "", category: "", date: null, topics: [] });
    }
  };

  return (
    <div className="p-4 bg-white shadow-md rounded-lg">
      <div>
        {/* Keyword Filter */}
        <Input
          placeholder="Search by keyword"
          value={filters.keyword}
          onChange={handleInputChange}
          className="w-full"
        />

        {/* Category Filter */}
        <Select
          placeholder="Select category"
          value={filters.category}
          onChange={handleCategoryChange}
          className="w-full mt-2"
        >
          <Option value="DESC">Mới nhất</Option>
          <Option value="ASC">Cũ nhất</Option>
        </Select>
      </div>

      {/* Topics Filter */}
      <div className="mt-4">
        <h3 className="text-base font-medium mb-2">Chủ đề:</h3>
        <div className="flex flex-wrap gap-2">
          {availableTopics.map((topic) => (
            <CheckableTag
              key={topic.id}
              checked={filters.topics.includes(topic.id)}
              onChange={(checked) => handleTopicChange(topic.id, checked)}
              className="border border-gray-300"
            >
              {topic.name}
            </CheckableTag>
          ))}
        </div>
      </div>

      {/* Calendar Filter */}
      {/* <div className="mt-4">
        <Calendar 
          fullscreen={false} 
          onSelect={handleDateSelect} 
        />
      </div> */}

      {/* Action Buttons */}
      <div className="mt-4 flex justify-end gap-2">
        <Button type="primary" onClick={handleFilter}>
          Lọc
        </Button>
        <Button onClick={handleReset}>
          Reset
        </Button>
      </div>
    </div>
  );
};

export default PostFilter;
