import { deleteData, getData, postData, putData } from "@/utils/FetchData";
import { Post, PostImage, Topic } from "../types";

// Topic
export const getTopic = async(): Promise<Topic[]> => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData('api/topic', token );
    return result.Data;
}

// Post

export const getPostDetail = async(postId: string | null): Promise<Post> => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData(`api/post/${postId}`, token );
    return result.Data;
}

export const createPost = async({ topicId, newPost }: { topicId: string, newPost: { header: string, content: string, postImage: PostImage[] } }) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/topic/${topicId}/post`, newPost, token);
    return result;
}

export const updatePost = async({ topicId, postId, newPost }: { topicId: string, postId: string, newPost: { header: string, content: string, postImage: PostImage[] } }) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await putData(`api/topic/${topicId}/post/${postId}`, newPost, token);
    return result;
}

export const deletePost = async(postId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await deleteData(`api/post/${postId}`, token);
    return result;
}

// Comment
export const createComment = async({ postId, newComment } : { postId: string, newComment: {content: string} }) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/post/${postId}/comment`, newComment, token);
    return result;
}

export const updateComment = async({ postId, commentId, newComment } : { postId: string, commentId: string, newComment: {content: string} }) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await putData(`api/post/${postId}/comment/${commentId}`, newComment, token);
    return result;
}

export const deleteComment = async(commentId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await deleteData(`api/post/comment/${commentId}`, token);
    return result;
}

// Reaction
export const likePost = async(postId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/post/${postId}/like`, {}, token);
    return result;
}

export const dislikePost = async(postId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/post/${postId}/dislike`, {}, token);
    return result;
}

export const likeComment = async(commentId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/post/comment/${commentId}/like`, {}, token);
    return result;
}

export const dislikeComment = async(commentId: string) => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData(`api/post/comment/${commentId}/dislike`, {}, token);
    return result;
}