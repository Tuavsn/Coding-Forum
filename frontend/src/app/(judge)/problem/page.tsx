import BreadCrumb from "@/components/common/BreadCrumb";
import ProblemWelComeCard from "@/components/judge/ProblemWelcomeCard";
import { Divider } from "antd";
import dynamic from "next/dynamic";

const ProblemList = dynamic(
    () => {
        return import("@/components/judge/ProblemList")
    }, {ssr: false}
)

export default function ProblemPage() {
    return (
        <div>
            <BreadCrumb type="problem"/>
            <ProblemWelComeCard />
            <Divider orientation="left">Danh sách bài tập</Divider>
            <ProblemList />
        </div>
    )
}