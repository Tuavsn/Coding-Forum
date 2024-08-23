import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import Comment from "@/components/common/comment/Comment";
import PostDetail from "@/components/home/post/PostDetail";

export default function PostPage() {
    return (
        <div>
            <BreadCrumb type="post"/>
            <PostDetail />
            <Comment />
        </div>
    )
}