import BreadCrumb from "@/components/common/BreadCrumb";
import PostDetail from "@/components/posts/PostDetail";

export default function PostPage() {
    return (
        <div>
            <BreadCrumb type="post"/>
            <PostDetail />
        </div>
    )
}