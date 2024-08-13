import { HomeOutlined, StarOutlined } from "@ant-design/icons";
import { Breadcrumb, Empty } from "antd";

export default function ContestPage() {
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
                                <StarOutlined />
                                <span>Cuộc Thi</span>
                            </>
                        )
                    }
                ]}
            />
            <Empty />
        </div>
    )
}