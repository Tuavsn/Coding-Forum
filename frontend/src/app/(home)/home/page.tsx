import BreadCrumb from "@/components/common/BreadCrumb";
import GroupFloatButton from "@/components/common/GroupFloatButton";
import HomeWelcomeCard from "@/components/home/HomeWelcomeCard";
import CommonStatistics from "@/components/posts/CommonStatistics";
import PostList from "@/components/posts/PostList";
import TopicList from "@/components/posts/TopicList";
import { getPost } from "@/libs/actions/post.acttion";
import { Post } from "@/libs/types";
import { Divider } from "antd";

export default async function HomePage() {

    /**
     * Fetch post from server
     */
    const posts: Post[] = await getPost();

    const sortedPost = [...posts].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

    return (
        <div>
            <BreadCrumb type="post" />
            <HomeWelcomeCard />
            <div className="grid grid-cols-4 gap-4">
                <div className="col-span-3">
                    <PostList posts={sortedPost} />
                </div>
                <div>
                    <Divider orientation="left"><p>Bộ lọc</p></Divider>
                    <TopicList />
                    <Divider orientation="left"><p>Thống kê</p></Divider>
                    {/* <CommonStatistics
                        topicCount={topics.length}
                        postCount={topics[0].posts.length}
                        userCount={topics[0].posts[0].user.username.length}
                        reactionCount={topics[0].posts[0].postReactions.length}
                        commentCount={topics[0].posts[0].postComment.length}
                    /> */}
                </div>
            </div>
            <GroupFloatButton />
        </div>
    )
}