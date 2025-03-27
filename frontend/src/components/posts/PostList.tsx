'use client'
import { PageableInfo, Post } from "@/libs/types";
import { formatDate, stringToSlug } from "@/libs/utils";
import { ClockCircleOutlined, TagsOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Card, Divider, Pagination, Tag, Typography } from "antd";
import type { PaginationProps } from "antd";
import Link from "next/link";
import List from "../common/List";
import PostAction from "./PostAction";
import { useRouter } from "next/navigation";


interface Props {
    posts: Post[];
    pageableInfo: PageableInfo;
}

export default function PostList(props: Props) {

    const { posts, pageableInfo } = props;

    const router = useRouter();

    const onchange: PaginationProps['onChange'] = (pageNumber, pageSize) => {
        let params = new URLSearchParams({
            page: pageNumber.toString(),
            size: pageSize.toString()
        }).toString();
        router.push('/?' + params);
    };
    
    return (
        <>
            <Divider orientation="left"><p>{posts.length} Bài Post</p></Divider>
            <Card className="shadow-md">
                <List
                    split={true}
                    bordered={true}
                    itemLayout="vertical"
                    dataSource={posts}
                    renderItem={(item: Post) => (
                        <div className="flex flex-col-reverse lg:flex-row items-center gap-4">
                            <div className="flex-1 p-2 min-w-0">
                                <div className="flex items-center gap-4">
                                    <Avatar shape="square" size={60} src={item.user.avatar} />
                                    <div>
                                        <div className="flex flex-row items-center gap-2">
                                            <Link href={`/post/${stringToSlug(item.header)}?id=${item.id}`}>
                                                <h3 className="font-bold text-lg">{item.header}</h3>
                                            </Link>
                                        </div>
                                        <div className="flex items-center text-gray-500 text-sm">
                                            <Link href={`/user?id=${item.user.id}`}>
                                                <strong className="mr-4">
                                                    <UserOutlined /> {item.user.username}
                                                </strong>
                                            </Link>
                                            <strong>
                                                <ClockCircleOutlined /> {formatDate(item.createdAt.toString())}
                                            </strong>
                                        </div>
                                    </div>
                                </div>
                                <Typography className="relative max-h-[160px] overflow-hidden mt-2">
                                    <div
                                        dangerouslySetInnerHTML={{ __html: item.content }}
                                        className="ck-content break-words whitespace-pre-wrap"
                                    />
                                    <div className="absolute bottom-0 left-0 right-0 h-12 bg-gradient-to-t from-white to-transparent">
                                        <Link
                                            href={`/post/${stringToSlug(item.header)}?id=${item.id}`}
                                            className="absolute inset-0 flex items-center justify-center bg-white/80 text-gray-600 font-medium shadow-lg cursor-pointer hover:shadow-xl"
                                        >
                                            Xem thêm
                                        </Link>
                                    </div>
                                </Typography>
                                {/* Post Action (like, dislike, comment, edit, delete, ...) */}
                                <PostAction
                                    post={item}
                                    allowLike={true}
                                    allowDislike={true}
                                    allowComment={true}
                                    allowEdit={true}
                                    allowDelete={true}
                                />
                                <div className="flex items-center text-gray-500 text-sm mx-2 mt-4">
                                    <TagsOutlined className="mr-2" />
                                    {item.topics && item.topics.map((topic) => (
                                        <Tag key={topic.id} color="blue">
                                            {topic.name}
                                        </Tag>
                                    ))}
                                </div>
                            </div>
                            {item.postImage && (
                                <img
                                    style={{ objectFit: "contain", width: "300px", height: "150px" }}
                                    alt={item.postImage[0]?.image}
                                    src={item.postImage[0]?.image}
                                />
                            )}
                        </div>
                    )}
                />
                {/* Pagination */}
                <Pagination
                    className="mt-4"
                    showQuickJumper
                    showSizeChanger
                    defaultCurrent={1}
                    defaultPageSize={5}
                    pageSizeOptions={['5', '10', '20']}
                    total={pageableInfo.totalElements}
                    onChange={onchange}
                />
            </Card>
        </>
    )
}