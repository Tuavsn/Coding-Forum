import { deleteData, getData, postData, putData } from "@/utils/FetchData";
import { Post, PostImage, Topic } from "../types";

// Topic
export const getTopic = async(): Promise<Topic[]> => {
    const result = await getData('api/topic');
    return result.Data;
}

// Post

export const getPostDetail = async(postId: string | null): Promise<Post> => {
    const result = await getData(`api/post/${postId}`);
    return result.Data;
}

export const createPost = async({ topicId, newPost }: { topicId: string, newPost: { header: string, content: string, postImage: PostImage[] } }) => {
    const result = await postData(`api/topic/${topicId}/post`, newPost);
    return result;
}

export const updatePost = async({ postId, newPost }: { postId: string, newPost: { header: string, content: string, postImage: PostImage[] } }) => {
    const result = await putData(`api/post/${postId}`, newPost);
    return result;
}

export const deletePost = async(postId: string) => {
    const result = await deleteData(`api/post/${postId}`);
    return result;
}

// Comment
export const createComment = async({ postId, newComment } : { postId: string, newComment: {content: string} }) => {
    const result = await postData(`api/post/${postId}/comment`, newComment);
    return result;
}

export const updateComment = async({ postId, commentId, newComment } : { postId: string, commentId: string, newComment: {content: string} }) => {
    const result = await putData(`api/post/${postId}/comment/${commentId}`, newComment);
    return result;
}

export const deleteComment = async(commentId: string) => {
    const result = await deleteData(`api/post/comment/${commentId}`);
    return result;
}

// Reaction
export const likePost = async(postId: string) => {
    const result = await postData(`api/post/${postId}/like`, {});
    return result;
}

export const dislikePost = async(postId: string) => {
    const result = await postData(`api/post/${postId}/dislike`, {});
    return result;
}

export const likeComment = async(commentId: string) => {
    const result = await postData(`api/post/comment/${commentId}/like`, {});
    return result;
}

export const dislikeComment = async(commentId: string) => {
    const result = await postData(`api/post/comment/${commentId}/dislike`, {});
    return result;
}