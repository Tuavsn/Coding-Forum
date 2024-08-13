import { BellOutlined, HomeOutlined } from "@ant-design/icons";
import { Breadcrumb, Empty } from "antd";

export default function NotifyPage() {
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
                                <BellOutlined />
                                <span>Thông báo</span>
                            </>
                        )
                    }
                ]}
            />
            <Empty />
        </div>
    )
}