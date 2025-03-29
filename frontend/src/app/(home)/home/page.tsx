import BreadCrumb from "@/components/common/BreadCrumb";
import GroupFloatButton from "@/components/common/GroupFloatButton";
import HomeWelcomeCard from "@/components/home/HomeWelcomeCard";
import CommonStatistics from "@/components/posts/CommonStatistics";
import PostList from "@/components/posts/PostList";
import TopicList from "@/components/posts/TopicList";
import { getPost } from "@/libs/actions/post.acttion";
import { PageableInfo, PageableRequest, Post, ResponseData } from "@/libs/types";
import { Divider } from "antd";

type Params = Promise<{ slug: string }>
type SearchParams = Promise<{ [key: string]: string | string[] | undefined }>

export default async function HomePage(props: {
    params: Params
    searchParams: SearchParams
}) {

    const searchParams = await props.searchParams;

    const pageable: PageableRequest = {
        page: searchParams.page ? parseInt(searchParams.page as string, 10) - 1 : 0,
        size: searchParams.size ? parseInt(searchParams.size as string, 10) : 5,
        sort: typeof searchParams.sort === 'string' ? searchParams.sort : 'createdAt,desc',
        search: typeof searchParams.search === 'string' ? searchParams.search : ''
    }

    /**
     * Fetch post from server
     */
    const data = (await getPost(pageable)).Data;

    const posts: Post[] = data.content;

    const pageableInfo: PageableInfo = {
        totalElements: data.totalElements,
        totalPages: data.totalPages
    }

    const sortedPost = [...posts].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

    return (
        <div>
            <BreadCrumb type="post" />
            <HomeWelcomeCard />
            <div className="grid grid-cols-4 gap-4">
                <div className="col-span-3">
                    <PostList
                        posts={sortedPost}
                        pageableInfo={pageableInfo}
                    />
                </div>
                <div>
                    <Divider orientation="left"><p>Bộ lọc</p></Divider>
                    <TopicList />
                    {/* <Divider orientation="left"><p>Thống kê</p></Divider> */}
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