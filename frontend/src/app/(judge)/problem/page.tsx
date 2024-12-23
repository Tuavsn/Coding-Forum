import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import ProblemWelcomeCard from "@/components/judge/problem-welcome-card/ProblemWelcomeCard";
import ProblemList from "@/components/judge/problem-list/ProblemList";
import { Card, Divider, Empty } from "antd";

export default function ProblemPage() {
    return (
        <div>
            <BreadCrumb type="problem"/>
            <ProblemWelcomeCard />
            <Divider orientation="left">Danh sách bài tập</Divider>
            {/* <Card>
                Feature under development
            </Card> */}
            <ProblemList />
        </div>
    )
}