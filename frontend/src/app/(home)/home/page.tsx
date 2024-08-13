import TopicList from "@/components/home/topic-list/TopicList";
import WelcomeCard from "@/components/home/wellcome-card/WelcomeCard";
import { HomeOutlined } from "@ant-design/icons";
import { Breadcrumb, Divider } from "antd";

export default function HomePage() {
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
                    }
                ]}
            />
            <WelcomeCard />
            <Divider orientation="left">Post mới</Divider>
            <div className="mt-2">
                <TopicList />
            </div>
            <></>
        </div>
    )
}