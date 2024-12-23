import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import ProblemWelcomeCard from "@/components/judge/problem-welcome-card/ProblemWelcomeCard";
import { Divider } from "antd";
import dynamic from "next/dynamic";

const ProblemList = dynamic(
    () => {
        return import("@/components/judge/problem-list/ProblemList")
    }, {ssr: false}
)

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