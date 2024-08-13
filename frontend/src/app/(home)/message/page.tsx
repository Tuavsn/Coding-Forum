import { HomeOutlined, MessageOutlined } from "@ant-design/icons";
import { Breadcrumb, Empty } from "antd";

export default function MessagePage() {
    return (
        <div>
            <Breadcrumb
                className="mb-2"
                items={[
                    {
                        title: (
                            <>
                                <HomeOutlined />
                                <span>Trang Chủ</span>
                            </>
                        )
                    },
                    {
                        title: (
                            <>
                                <MessageOutlined />
                                <span>Tin Nhắn</span>
                            </>
                        )
                    }
                ]}
            />
            <Empty />
        </div>
    )
}