'use client'

import { getPersonalPosts, getUserProfile, updateProfile } from "@/libs/actions/user.actions"
import { Post, User } from "@/libs/types"
import { Avatar, Button, List, Space, Tag } from "antd"
import React from "react"
import { useQuery, useQueryClient } from "react-query"
import Link from "next/link"
import { formatDate, stringToSlug } from "@/libs/utils"
import { ClockCircleOutlined, DislikeFilled, DislikeOutlined, LikeFilled, LikeOutlined, MessageFilled, MessageOutlined, UserOutlined } from "@ant-design/icons"
import { ReactionType } from "@/libs/enum"
import { useSearchParams } from "next/navigation"

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function UserProfile() {
    const queryClient = useQueryClient()

    const userId = useSearchParams().get('id')
    
    const {data: userProfile} = useQuery<User>('getUserProfile', () => getUserProfile(userId))

    const {data: userPosts} = useQuery<Post[]>(['getUserPosts'], () => getPersonalPosts(userId))

    return (
        <>
            <div className="bg-gray-100">
                <div className="container mx-auto py-8">
                    <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4">
                        <div className="col-span-4 sm:col-span-3">
                            <div className="bg-white shadow rounded-lg p-6">
                                <div className="flex flex-col items-center">
                                    <img src={userProfile?.avatar} className="w-32 h-32 bg-gray-300 rounded-full mb-4 shrink-0"/>
                                    <h1 className="text-xl font-bold">{userProfile?.username}</h1>
                                    <div className="flex">
                                        <Tag color={userProfile?.role === "SYS_ADMIN" ? '#108ee9' : '#87d068'}>{userProfile?.role}</Tag>
                                        <Tag color={
                                            (() => {
                                                switch(userProfile?.achievement) {
                                                    case "BEGINNER":
                                                        return "#87d068"
                                                    case "INTERMEDIATE":
                                                        return "#108ee9"
                                                    default:
                                                        return "#f50"
                                                }
                                            })()
                                        }>{userProfile?.achievement}</Tag>
                                    </div>
                                </div>
                                <hr className="my-6 border-t border-gray-300"/>
                                {/* <div className="flex justify-center">
                                    <a href="#" className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded" onClick={showUpdateDrawer}>Cập nhật thông tin</a>
                                </div> */}
                            </div>
                        </div>
                        <div className="col-span-4 sm:col-span-9">
                            <div className="bg-white shadow rounded-lg p-6">
                                <div className="flex flex-col">
                                    <span className="text-gray-700 uppercase font-bold tracking-wider mb-2">Thông tin</span>
                                    <ul>
                                        <li className="mb-2"><strong>Email:</strong> {userProfile?.email}</li>
                                        <li className="mb-2"><strong>Giới tính:</strong> {userProfile?.gender === "MALE" ? "Nam" : "Nữ"}</li>
                                        <li className="mb-2"><strong>Số điện thoại:</strong> {userProfile?.phone}</li>
                                        <li className="mb-2"><strong>Địa chỉ:</strong> {userProfile?.address}</li>
                                    </ul>
                                </div>
                                <hr className="my-6 border-t border-gray-300"/>
                                <div className="flex flex-col">
                                    <span className="text-gray-700 uppercase font-bold tracking-wider mb-2">Danh sách bài post</span>
                                    {userPosts && (
                                        <List 
                                            size="large"
                                            className="my-2"
                                            bordered={false}
                                            split={true}
                                            itemLayout="vertical" 
                                            pagination={{
                                                pageSize: 10
                                            }}
                                            dataSource={userPosts}
                                            renderItem={(item) => (
                                                <List.Item
                                                    className="px-0 items-center"
                                                    actions={[
                                                        <Button 
                                                            className="border-none px-2 shadow-none" key="list-vertical-message"
                                                            // onClick={() => handleLikePost(item.id)}
                                                        >
                                                            <IconText 
                                                                icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === userProfile?.id)) ? LikeFilled : LikeOutlined} 
                                                                text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                                                            />
                                                        </Button>,
                                                        <Button 
                                                            className="border-none px-2 shadow-none" key="list-vertical-message"
                                                            // onClick={() => handleDislikePost(item.id)}
                                                        >
                                                            <IconText 
                                                                icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === userProfile?.id)) ? DislikeFilled : DislikeOutlined} 
                                                                text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                                                            />
                                                        </Button>,
                                                        <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                                            <IconText 
                                                                icon={item.postComment.some((comment) => comment.user.id === userProfile?.id) ? MessageFilled : MessageOutlined} 
                                                                text={item.postComment.length.toString()}
                                                            />
                                                        </Button>,
                                                    ]}
                                                >
                                                    <List.Item.Meta
                                                        style={{overflow: "hidden"}}
                                                        avatar={<Avatar shape="square" size={60} src={item.user.avatar}/>}
                                                        title={<Link href={`/post/${stringToSlug(item.header)}?id=${item.id}`}><strong>{item.header}</strong></Link>}
                                                        description={(
                                                            <>
                                                                <strong className="mr-6"><Link href="/user"><UserOutlined /> {item.user.username}</Link></strong>
                                                                <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                                            </>
                                                        )}
                                                    />
                                                </List.Item>
                                        )}>
                                        </List>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
