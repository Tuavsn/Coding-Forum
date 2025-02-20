import { AreaChartOutlined } from "@ant-design/icons";
import { Card } from "antd";

export default function RankingWelcomeCard() {
    return (
        <Card 
            className="my-6 shadow-md" 
            title={(
                <><AreaChartOutlined style={{color: "#1677ff"}} /> Hệ thống xếp hạng của Coding Forum</>
            )} 
            bordered style={{width: '100%'}} 
        >
            <div>
                <span>
                    - Chào mừng đến với bảng xếp hạng.<br />
                    - Bảng xếp hạng này được sắp xếp theo tổng điểm mà bạn đạt được khi hoàn thành các bài tập.<br/>
                    - Hãy cố gắng đạt được thứ hạng cao nhất nhé.<br/>
                </span>
            </div>
        </Card>
    )
}