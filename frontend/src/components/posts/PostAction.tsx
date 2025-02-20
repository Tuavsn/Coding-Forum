'use client'

import usePost from "@/hooks/usePost";
import { Post } from "@/libs/types";
import { DeleteOutlined, DislikeFilled, DislikeOutlined, EditOutlined, LikeFilled, LikeOutlined, MessageFilled, MessageOutlined } from "@ant-design/icons";
import { Button, Divider, Popconfirm, Space, Tooltip } from "antd";
import PostModal from "./PostModal";

interface Props {
    // Post id
    post: Post;
    // Event props
    allowEdit?: boolean;
    allowDelete?: boolean;
    allowLike?: boolean;
    allowDislike?: boolean;
    allowComment?: boolean;
}

export default function PostAction(props: Props) {

    const {
        post,
        allowEdit,
        allowDelete,
        allowLike,
        allowDislike,
        allowComment,
    } = props;

    const {
        isLiked,
        isDisliked,
        isCommented,
        likesCount,
        disLikesCount,
        commentsCount,
        postCreateLoading,
        postUpdateLoading,
        postDeleteLoading,
        handleLike,
        handleDislike,
        handleComment,
        handleCreate,
        handleUpdate,
        handleDelete,
        isOpenModal,
        toggleModal,
        handleInputChange,
        postContent
    } = usePost({ post})

    return (
        <>
            {/* Action Bar */}
            <Space>
                {/* Like button */}
                {allowLike && (
                    <Tooltip title="Like">
                        <Button
                            type="text"
                            icon={
                                isLiked ? <LikeFilled className="text-lg" /> : <LikeOutlined className="text-lg" />
                            }
                            onClick={() => handleLike(post.id)}
                        >
                            {likesCount}
                        </Button>
                    </Tooltip>
                )}
                <Divider type="vertical" />
                {/* Dislike button */}
                {allowDislike && (
                    <Tooltip title="Dislike">
                        <Button
                            type="text"
                            icon={
                                isDisliked ? <DislikeFilled className="text-lg" /> : <DislikeOutlined className="text-lg" />
                            }
                            onClick={() => handleDislike(post.id)}
                        >
                            {disLikesCount}
                        </Button>
                    </Tooltip>    
                )}
                <Divider type="vertical" />
                {/* Comment button */}
                {allowComment && (
                    <Tooltip title="Bình luận">
                        <Button
                            type="text"
                            icon={
                                isCommented ? <MessageFilled className="text-lg" /> : <MessageOutlined className="text-lg" />
                            }
                            onClick={() => handleComment()}
                        >
                            {commentsCount}
                        </Button>
                    </Tooltip>
                )}
                <Divider type="vertical" />
                {/* Edit button */}
                {allowEdit && (
                    <Tooltip title="Chỉnh sửa">
                        <Button
                            type="text"
                            icon={<EditOutlined className="text-lg" />}
                            onClick={() => toggleModal('update')}
                        />
                    </Tooltip>
                )}
                <Divider type="vertical" />
                {/* Delete button */}
                {allowDelete && (
                    <Tooltip title="Xóa">
                        <Popconfirm
                            title="Bạn có chắc muốn xóa không?"
                            onConfirm={() => handleDelete()}
                            okText="Có"
                            cancelText="Không"
                        >
                            <Button type="text" danger icon={<DeleteOutlined className="text-lg" />} loading={postDeleteLoading} />
                        </Popconfirm>
                    </Tooltip>    
                )}
            </Space>
            <PostModal
                isOpen={isOpenModal}
                postContent={postContent}
                toggleAction={toggleModal}
                onChange={handleInputChange}
                onUpdate={handleUpdate}
                onCreate={handleCreate}
                isCreateLoading={postCreateLoading}
                isUpdateLoading={postUpdateLoading}
            />
        </>
    )
}