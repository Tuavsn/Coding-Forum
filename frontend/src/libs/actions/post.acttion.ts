import { getData, postData } from "@/utils/FetchData";

export const getTopic = async(): Promise<Topic[]> => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData('api/topic', token );
    return result.Data;
}

export const getPostDetail = async(postId: string | null): Promise<Post> => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData(`api/post/${postId}`, token );
    return result.Data;
}

export const createPost = async(newPost: {topic: { id: string }, header: string, content: string}) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData('api/post', newPost, token);
    return result.Data
}

export const createComment = async(newComment: {post: {id: string}, content: string}) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData('api/post/create-comment', newComment, token);
    return result.Data
}