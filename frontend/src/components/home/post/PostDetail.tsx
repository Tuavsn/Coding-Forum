'use client'

import Loading from "@/components/common/loading/Loading";
import { getPostDetail } from "@/libs/actions/post.acttion";
import { formatDate } from "@/libs/utils";
import { 
    ClockCircleOutlined, 
    DislikeOutlined, 
    HeartOutlined, 
    LikeOutlined, 
    MessageOutlined, 
    SmileOutlined, 
    UserOutlined 
} from "@ant-design/icons";
import { Avatar, Card, Carousel, Divider, Image, Space, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import { useSearchParams } from "next/navigation";
import React from "react";
import { useQuery } from "react-query";

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function PostDetail() {

    const postId = useSearchParams().get('id')

    const { data, isLoading } = useQuery<Post>('getPostDetail', () => getPostDetail(postId))

    if(isLoading) {
        return (<Loading />)
    }

    return (
        data && (
            <Card
                className="w-full"
                actions={[
                    <IconText icon={HeartOutlined} text="222" key="list-vertical-message" />,
                    <IconText icon={SmileOutlined} text="242" key="list-vertical-message" />,
                    <IconText icon={LikeOutlined} text="333" key="list-vertical-message" />,
                    <IconText icon={DislikeOutlined} text="232" key="list-vertical-message" />,
                    <IconText icon={MessageOutlined} text="244" key="list-vertical-message" />
                ]}
            >
                <Meta
                    avatar={<Avatar shape="square" size={60} src={data.user.avatar} />}
                    title={
                        <div>
                            <p className="text-xl">{data.header}</p>
                            <p className="text-base text-slate-400 mt-1">
                                <Link href="/user"><UserOutlined /> {data.user.username}</Link>
                                <ClockCircleOutlined className="ml-4" /> {formatDate(data.createdAt.toString())}
                            </p>
                        </div>
                    }
                />
                <Divider />
                {data.postImage && (
                    <Carousel arrows style={{backgroundColor: "black"}}>
                        {data.postImage.map((image, index) => (
                            <Image key={index} src={image.image} alt={image.image} />
                        ))}
                    </Carousel>
                )}
                <Typography className="mt-2">
                    {data.content}
                </Typography>
            </Card>
        )
    )
}