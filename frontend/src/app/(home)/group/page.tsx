import { BankOutlined, HomeOutlined } from "@ant-design/icons";
import { Breadcrumb, Empty } from "antd";

export default function GroupPage() {
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
                                <BankOutlined />
                                <span>Nhóm</span>
                            </>
                        )
                    }
                ]}
            />
            <Empty />
        </div>
    )
}