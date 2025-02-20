import RankingList from "@/components/home/RankingList";
import RankingWelcomeCard from "@/components/home/RankingWelcomeCard";
import { AreaChartOutlined, HomeOutlined } from "@ant-design/icons";
import { Breadcrumb, Card, Divider, Empty } from "antd";

export default function RankingPage() {

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
                                <AreaChartOutlined />
                                <span>Xếp Hạng</span>
                            </>
                        )
                    }
                ]}
            />
            <RankingWelcomeCard />
            <Divider orientation="left">Bảng xếp hạng</Divider>
            <div className="mt-2">
                <RankingList />
            </div>
        </div>
    )
}