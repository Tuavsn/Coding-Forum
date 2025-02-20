import { HeartFilled } from "@ant-design/icons";
import { Card } from "antd";

export default function HomeWelcomeCard() {
    return (
        <Card 
            className="my-6 shadow-md" 
            title={(
                <><HeartFilled style={{color: "red"}} /> Chào mừng đến với Coding Forum</>
            )} 
            bordered style={{width: '100%'}} 
        >
            <div>
                <span>
                    - Coding Forum là diễn đàn về lập trình có tích hợp Online Judge.<br/>
                    - Hệ thống bài tập của Coding Forum được phân loại chi tiết và được thiết kế để phù hợp với những bạn mới học lập trình.<br/>
                    - Coding Forum miễn phí cho bất kỳ ai có mong muốn học lập trình.<br />
                    - Còn chờ gì nữa, hãy đăng ký tài khoản. Sau đó, tham khảo lộ trình học tập rồi thử sức với bài tập đầu tiên nhé.<br/>
                </span>
            </div>
        </Card>
    )
}