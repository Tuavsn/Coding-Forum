import { CodeOutlined } from "@ant-design/icons";
import { Card } from "antd";

export default function ProblemWelComeCard() {
    return (
        <Card 
            className="my-6" 
            title={(
                <><CodeOutlined style={{color: "#1677ff", fontSize: '.9rem'}} /> Hệ thống bài tập của StudentCodeHub</>
            )} 
            bordered style={{width: '100%'}} 
        >
            <div>
                <span>
                    - Hệ thống bài tập của StudentCodeHub được phân loại chi tiết và được thiết kế để phù hợp với những bạn mới học lập trình.<br/>
                    - Bài tập trên hệ thống sẽ chia ra các cấp độ bao gồm: Dễ, Trung bình và Khó.<br />
                    - Số điểm làm bài sẽ được tính vào tổng điểm của bạn, đồng thời hệ thống sẽ xếp hạng dựa trên tổng điểm của bạn.<br/>
                </span>
            </div>
        </Card>
    )
}