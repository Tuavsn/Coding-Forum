import { deleteData, getPublicData, postData, putData } from "@/utils/FetchData";
import { PageableRequest, Post, PostImage, ResponseData, Topic } from "../types";
import { ReactionType } from "../enum";

// Topic
export const getTopic = async(): Promise<Topic[]> => {
    const result = await getPublicData('api/v1/topic');
    return result.Data;
}

// Post
export const getPost = async(pageable: PageableRequest): Promise<ResponseData> => {
    const params = new URLSearchParams(pageable as any).toString();
    const result = await getPublicData(`api/v1/post/all?${params}`);
    return result;
}

export const getPostDetail = async(postId: string | null): Promise<Post> => {
    const result = await getPublicData(`api/v1/post/${postId}`);
    return result.Data;
}

export const createPost = async({ newPost }: { newPost: { topic: Topic[], header: string, content: string, postImage: PostImage[] } }) => {
    const result = await postData(`api/v1/post`, newPost);
    return result;
}

export const updatePost = async({ postId, newPost }: { postId: string, newPost: { header: string, content: string, postImage: PostImage[] } }) => {
    const result = await putData(`api/v1/post/${postId}`, newPost);
    return result;
}

export const deletePost = async(postId: string) => {
    const result = await deleteData(`api/v1/post/${postId}`);
    return result;
}

// Comment
export const createComment = async({ postId, newComment } : { postId: string, newComment: {content: string} }) => {
    const result = await postData(`api/v1/post/${postId}/comment`, newComment);
    return result;
}

export const updateComment = async({ postId, commentId, newComment } : { postId: string, commentId: string, newComment: {content: string} }) => {
    const result = await putData(`api/v1/post/${postId}/comment/${commentId}`, newComment);
    return result;
}

export const deleteComment = async(commentId: string) => {
    const result = await deleteData(`api/v1/post/comment/${commentId}`);
    return result;
}

// Reaction
export const likePost = async(postId: string) => {
    const result = await postData(`api/v1/post/${postId}/react?reactionType=${ReactionType.LIKE}`, {});
    return result;
}

export const dislikePost = async(postId: string) => {
    const result = await postData(`api/v1/post/${postId}/react?reactionType=${ReactionType.DISLIKE}`, {});
    return result;
}

export const likeComment = async(commentId: string) => {
    const result = await postData(`api/v1/post/comment/${commentId}/react?reactionType=${ReactionType.LIKE}`, {});
    return result;
}

export const dislikeComment = async(commentId: string) => {
    const result = await postData(`api/v1/post/comment/${commentId}/react?reactionType=${ReactionType.DISLIKE}`, {});
    return result;
}