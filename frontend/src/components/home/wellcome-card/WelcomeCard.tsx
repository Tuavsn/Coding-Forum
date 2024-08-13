import { HeartFilled } from "@ant-design/icons";
import { Card } from "antd";

export default function WellcomeCard() {
    return (
        <Card 
            className="my-6" 
            title={(
                <><HeartFilled style={{color: "red"}} /> Chào mừng đến với StudentCodeHub</>
            )} 
            bordered style={{width: '100%'}} 
        >
            <div>
                <span>
                    - StudentCodeHub là diễn đàn về lập trình có tích hợp Online Judge.<br/>
                    - Hệ thống bài tập của StudentCodeHub được phân loại chi tiết và được thiết kế để phù hợp với những bạn mới học lập trình.<br/>
                    - StudentCodeHub miễn phí cho bất kỳ ai có mong muốn học lập trình.<br />
                    - Còn chờ gì nữa, hãy đăng ký tài khoản. Sau đó, tham khảo lộ trình học tập rồi thử sức với bài tập đầu tiên nhé.<br/>
                </span>
            </div>
        </Card>
    )
}