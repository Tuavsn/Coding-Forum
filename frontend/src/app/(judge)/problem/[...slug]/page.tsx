import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import ProblemDetail from "@/components/judge/problem/ProblemDetail";
import { Empty } from "antd";

const exampleData = {
    problem: {
        id: '1',
        user: {
            username: "Admin1",
            avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
        },
        header: 'C++',
        totalComment: '10',
        description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
        createAt: '19/8/2024 10:23 am'
    }
}

export default function ProblemSubmitPage() {
    return (
        <div>
            <BreadCrumb type="problem"/>
            <ProblemDetail problem={exampleData.problem}/>
        </div>
    )
}