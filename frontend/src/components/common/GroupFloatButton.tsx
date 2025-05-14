'use client'
import { PlusOutlined } from "@ant-design/icons";
import { FloatButton } from "antd";
import { usePathname } from "next/navigation";
import PostModal from "../posts/PostModal";
import { useContext } from "react";
import { AuthContext } from "@/libs/context/AuthContextProvider";
import usePost from "@/libs/hooks/usePost";

export default function GroupFloatButton() {

    const {auth} = useContext(AuthContext);

    const path = usePathname();

    const {
        isOpenModal,
        postContent,
        toggleModal,
        handleInputChange,
        handleCreate,
        postCreateLoading,
    } = usePost({});

    return (
        <>
            <FloatButton.Group shape="circle" style={{ insetInlineEnd: 24 }}>
                {path === '/home' && auth && (
                    <FloatButton
                        icon={<PlusOutlined />}
                        tooltip={<div>Đăng bài</div>}
                        onClick={() => toggleModal('create')}
                    />
                )}
                {path === '/problem' && auth && auth.role === 'SYS_ADMIN' && (
                    <FloatButton
                        icon={<PlusOutlined />}
                        tooltip={<div>Tạo Problem</div>}
                    />
                )}
                <FloatButton.BackTop 
                    tooltip={<div>Trở về đầu trang</div>}
                    visibilityHeight={0} 
                />
            </FloatButton.Group>
            <PostModal
                isOpen={isOpenModal}
                postContent={postContent}
                toggleAction={toggleModal}
                onChange={handleInputChange}
                onCreate={handleCreate}
                isCreateLoading={postCreateLoading}
            />
        </>
    )
}