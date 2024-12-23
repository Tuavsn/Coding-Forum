import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import ProblemWelcomeCard from "@/components/judge/problem-welcome-card/ProblemWelcomeCard";
import ProblemList from "@/components/judge/problem-list/ProblemList";
import { Divider } from "antd";

export default function ProblemPage() {
    return (
        <div>
            <BreadCrumb type="problem"/>
            <ProblemWelcomeCard />
            <Divider orientation="left">Danh sách bài tập</Divider>
            <ProblemList />
        </div>
    )
}