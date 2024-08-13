import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import TopicList from "@/components/home/topic-list/TopicList";
import { Divider, Empty } from "antd";

export default function PostPage() {
    return (
        <div>
            <BreadCrumb type="post"/>
            <Divider orientation="left">Post mới</Divider>
            <div className="mt-2">
                <TopicList />
            </div>
        </div>
    )
}