'use client'

import { AuthContext } from "@/context/AuthContextProvider"
import { getPersonalPosts, updateProfile } from "@/libs/actions/user.actions"
import { FileType, Post } from "@/libs/types"
import { Avatar, Button, Col, Drawer, Form, Input, List, message, Radio, RadioChangeEvent, Row, Space, Spin, Tag, Upload, UploadFile, UploadProps } from "antd"
import React, { useContext, useState } from "react"
import { useMutation, useQuery, useQueryClient } from "react-query"
import Link from "next/link"
import { formatDate, getBase64, stringToSlug } from "@/libs/utils"
import { AimOutlined, ClockCircleOutlined, DislikeFilled, DislikeOutlined, FireOutlined, LikeFilled, LikeOutlined, LoadingOutlined, MailOutlined, MessageFilled, MessageOutlined, PhoneOutlined, QuestionCircleOutlined, UserOutlined } from "@ant-design/icons"
import { Gender, ReactionType } from "@/libs/enum"
import { uploadButton } from "../common/upload/UploadButton"

const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

export default function PersonalProfile() {
    const queryClient = useQueryClient()

    const {auth, setAuth} = useContext(AuthContext)

    const {data, isLoading} = useQuery<Post[]>(['getPersonalPosts', auth?.id], () => getPersonalPosts(auth?.id))

    const [profileUpdateLoading, setProfileUpdateLoading] = useState(false)

    const [openDrawer, setOpenDrawer] = useState(false)

    const [username, setUsername] = useState('')

    const [password, setPassword] = useState('')

    const [avatar, setAvatar] = useState('')

    const [address, setAddress] = useState('')

    const [phone, setPhone] = useState('')

    const [gender, setGender] = useState<Gender>(Gender.MALE)

    const [fileList, setFileList] = useState<UploadFile[]>([])

    // update profile
    const profileUpdateMutation = useMutation(updateProfile, {
        onMutate: () => {
            setProfileUpdateLoading(true)
        },

        onSuccess: (data) => {
            setProfileUpdateLoading(false)
            setOpenDrawer(false)
            queryClient.invalidateQueries('getUserInfo')
            setUsername('')
            setAvatar('')
            setAddress('')
            setPhone('')
            setFileList([])
            setGender(Gender.MALE)
            message.success(data.Message)
        }
    })

    const handleUpdateProfile = () => {
        profileUpdateMutation.mutate({
            username: username,
            address: address,
            gender: gender,
            phone: phone,
            avatar: avatar,
            password: password
        })
    }

    // common
    const showUpdateDrawer = () => {
        if(auth) {
            setUsername(auth?.username)
            setAvatar(auth?.avatar)
            setAddress(auth?.address)
            setPhone(auth?.phone)
            setFileList([])
            setOpenDrawer(true)
        }
    }

    const closeDrawer = () => {
        setUsername('')
        setAvatar('')
        setAddress('')
        setPhone('')
        setFileList([])
        setGender(Gender.MALE)
        setOpenDrawer(false)
    }

    const handleChange: UploadProps['onChange'] = async ({ fileList: newFileList }) => {
        const updatedFileList = await Promise.all(
            newFileList.map(async (file) => {
              if (file.originFileObj && !file.url && !file.preview) {
                const base64 = await getBase64(file.originFileObj as FileType)
                return {
                  ...file,
                  url: base64,
                }
              }
              return file
            })
        )

        setFileList(updatedFileList)

        const avatar = updatedFileList.length > 0 ? updatedFileList[0].url as string : "";

        setAvatar(avatar);
    }

    return (
        <>
            <div className="bg-gray-100">
                <div className="container mx-auto py-8">
                    <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4">
                        <div className="col-span-4 sm:col-span-3">
                            <div className="bg-white shadow rounded-lg p-6">
                                <div className="flex flex-col items-center">
                                    <img src={auth?.avatar} className="w-32 h-32 bg-gray-300 rounded-full mb-4 shrink-0"/>
                                    <h1 className="text-xl font-bold">{auth?.username}</h1>
                                    <div className="flex">
                                        <Tag color={auth?.role === "SYS_ADMIN" ? '#108ee9' : '#87d068'}>{auth?.role}</Tag>
                                        <Tag color={
                                            (() => {
                                                switch(auth?.achievement) {
                                                    case "BEGINNER":
                                                        return "#87d068"
                                                    case "INTERMEDIATE":
                                                        return "#108ee9"
                                                    default:
                                                        return "#f50"
                                                }
                                            })()
                                        }>{auth?.achievement}</Tag>
                                    </div>
                                </div>
                                <hr className="my-6 border-t border-gray-300"/>
                                <div className="flex justify-center">
                                    <a href="#" className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded" onClick={showUpdateDrawer}>Cập nhật thông tin</a>
                                </div>
                            </div>
                        </div>
                        <div className="col-span-4 sm:col-span-9">
                            <div className="bg-white shadow rounded-lg p-6">
                                <div className="flex flex-col">
                                    <span className="text-gray-700 uppercase font-bold tracking-wider mb-2">Thông tin</span>
                                    <ul>
                                        <li className="flex gap-2 items-center mb-2">
                                            <MailOutlined /><strong>Email:</strong> {auth?.email}
                                        </li>
                                        <li className="flex gap-2 items-center mb-2">
                                            <QuestionCircleOutlined /><strong>Giới tính:</strong> {auth?.gender === "MALE" ? "Nam" : "Nữ"}
                                        </li>
                                        <li className="flex gap-2 items-center mb-2">
                                            <PhoneOutlined /><strong>Số điện thoại:</strong> {auth?.phone}
                                        </li>
                                        <li className="flex gap-2 items-center mb-2">
                                            <AimOutlined /><strong>Địa chỉ:</strong> {auth?.address}
                                        </li>
                                        <li className="flex gap-2 items-center mb-2">
                                            <FireOutlined /><strong>Tổng điểm:</strong> {auth?.totalSubmissionPoint}
                                        </li>
                                    </ul>
                                </div>
                                <hr className="my-6 border-t border-gray-300"/>
                                {isLoading ? (
                                    <Spin indicator={<LoadingOutlined style={{ fontSize: 48 }} spin />} />
                                ) : (
                                    <div className="flex flex-col">
                                        <span className="text-gray-700 uppercase font-bold tracking-wider mb-2">Danh sách bài post</span>
                                        {data && (
                                            <List 
                                                size="large"
                                                className="my-2"
                                                bordered={false}
                                                split={true}
                                                itemLayout="vertical" 
                                                pagination={{
                                                    pageSize: 10
                                                }}
                                                dataSource={data}
                                                renderItem={(item) => (
                                                    <List.Item
                                                        className="px-0 items-center"
                                                        actions={[
                                                            <Button 
                                                                className="border-none px-2 shadow-none" key="list-vertical-message"
                                                                // onClick={() => handleLikePost(item.id)}
                                                            >
                                                                <IconText 
                                                                    icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id)) ? LikeFilled : LikeOutlined} 
                                                                    text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.LIKE).length.toString()}
                                                                />
                                                            </Button>,
                                                            <Button 
                                                                className="border-none px-2 shadow-none" key="list-vertical-message"
                                                                // onClick={() => handleDislikePost(item.id)}
                                                            >
                                                                <IconText 
                                                                    icon={item.postReactions.some((reaction => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id)) ? DislikeFilled : DislikeOutlined} 
                                                                    text={item.postReactions.filter((reaction) => reaction.reactionType === ReactionType.DISLIKE).length.toString()}
                                                                />
                                                            </Button>,
                                                            <Button className="border-none px-2 shadow-none" key="list-vertical-message">
                                                                <IconText 
                                                                    icon={item.postComment.some((comment) => comment.user.id === auth?.id) ? MessageFilled : MessageOutlined} 
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
                                                                    <strong className="mr-6"><Link href="#"><UserOutlined /> {item.user.username}</Link></strong>
                                                                    <strong><ClockCircleOutlined /> {formatDate(item.createdAt.toString())}</strong>
                                                                </>
                                                            )}
                                                        />
                                                    </List.Item>
                                            )}>
                                            </List>
                                        )}
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <Drawer
                width={720}
                onClose={closeDrawer}
                open={openDrawer}
                styles={{
                body: {
                    paddingBottom: 80,
                },
                }}
                extra={
                <Space>
                    <Button onClick={closeDrawer}>Hủy</Button>
                    <Button onClick={handleUpdateProfile} type="primary" loading={profileUpdateLoading}>
                        Cập nhật
                    </Button>
                </Space>
                }
                >
                <Form layout="vertical" hideRequiredMark>
                    <Row gutter={16}>
                        <Col span={22}>
                            <Form.Item
                                label="Username"
                                rules={[{ required: true, message: 'Nhập Username' }]}
                            >
                                <Input style={{width: '100%'}} onChange={(e) => setUsername(e.target.value)} value={username} placeholder="Nhập Username" />
                            </Form.Item>
                            <Form.Item
                                label="Địa chỉ"
                                rules={[{ required: true, message: 'Nhập địa chỉ' }]}
                            >
                                <Input style={{width: '100%'}} onChange={(e) => setAddress(e.target.value)} value={address} placeholder="Nhập Địa chỉ" />
                            </Form.Item>
                            <Form.Item
                                label="Số điện thoại"
                                rules={[{ required: true, message: 'Nhập số điện thoại' }]}
                            >
                                <Input style={{width: '100%'}} onChange={(e) => setPhone(e.target.value)} value={phone} placeholder="Nhập Số điện thoại" />
                            </Form.Item>
                            <Form.Item
                                label="Giới tính"
                                rules={[{ required: true, message: 'Chọn giới tính' }]}
                            >
                                <Radio.Group value={gender}>
                                    <Radio value={Gender.MALE} onChange={(e: RadioChangeEvent) => setGender(e.target.value)}> Nam </Radio>
                                    <Radio value={Gender.FEMALE} onChange={(e: RadioChangeEvent) => setGender(e.target.value)}> Nữ </Radio>
                                </Radio.Group>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={22}>
                            <Form.Item
                                label="Hình ảnh"
                                rules={[
                                {
                                    required: true,
                                    message: 'Upload ảnh bài viết',
                                },
                                ]}
                            >
                                <Upload
                                    listType="picture-card"
                                    fileList={fileList}
                                    onChange={handleChange}
                                    beforeUpload={() => false}
                                    maxCount={1}
                                >
                                    {fileList.length >= 1 ? null : uploadButton}
                                </Upload>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    )
}
