import BreadCrumb from "@/components/common/BreadCrumb";
import TopicList from "@/components/posts/TopicList";
import { Divider } from "antd";

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