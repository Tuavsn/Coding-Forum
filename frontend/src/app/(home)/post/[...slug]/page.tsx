import BreadCrumb from "@/components/common/BreadCrumb";
import PostDetail from "@/components/posts/PostDetail";

type Params = Promise<{ slug: string }>
type SearchParams = Promise<{ [key: string]: string | string[] | undefined }>

export default async function PostPage(props: {
    params: Params
    searchParams: SearchParams
}) {
    const searchParams = await props.searchParams;
    const postId = searchParams.id;
    return (
        <div>
            <BreadCrumb type="post"/>
            <PostDetail postId={postId}/>
        </div>
    )
}