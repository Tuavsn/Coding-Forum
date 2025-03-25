'use client'
import { PlusOutlined } from "@ant-design/icons";
import { FloatButton } from "antd";
import { usePathname } from "next/navigation";
import PostModal from "../posts/PostModal";
import usePost from "@/hooks/usePost";

export default function GroupFloatButton() {

    const path = usePathname();

    const {
        toggleModal,
    } = usePost({});

    return (
        <>
            <FloatButton.Group shape="circle" style={{ insetInlineEnd: 24 }}>
                {path === '/home' && (
                    <FloatButton
                        icon={<PlusOutlined />}
                        tooltip={<div>Đăng bài</div>}
                        onClick={() => toggleModal('create')}
                    />
                )}
                {path === '/problem' && (
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
        </>
    )
}