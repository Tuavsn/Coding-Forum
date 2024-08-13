import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import Comment from "@/components/common/comment/Comment";
import PostDetail from "@/components/home/post/PostDetail";
import { Divider, Empty } from "antd";

const exampleData = {
    post: {
        id: '3',
        user: {
            username: "admin1",
            avatar: "https://api.dicebear.com/7.x/miniavs/svg?seed=5"
        },
        header: 'Top công ty công nghệ cho lập trình viên ở VN',
        totalComment: '10',
        description: "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
        postImage: [
            "https://oj.vnoi.info/martor/e55db622-bfc8-434e-a3c5-f173aa105dd9.png",
            "https://oj.vnoi.info/martor/d98d2b60-4c82-459d-8ca6-cf47c6951a61.png",
            "https://oj.vnoi.info/martor/9f80e1b8-422d-4ccb-87bc-4ec2540d5d3b.png"
        ],
        createAt: '19/8/2024 10:23 am'
    }
}

export default function PostPage() {
    return (
        <div>
            <BreadCrumb type="post"/>
            <PostDetail post={exampleData.post}/>
            <Divider orientation="left"><p className="text-xl">Bình luận</p></Divider>
            <Comment />
        </div>
    )
}