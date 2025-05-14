import { PageableRequest, Post, PostImage, ResponseData, Topic } from "../constant/types";
import { ReactionType } from "../constant/enum";
import { ApiUtil } from "../utils/apiUtil";
import { ApiEndPoint } from "../constant/constant";

export const PostService = {
    // Topic
    getTopics: async(): Promise<Topic[]> => {
        const result = await ApiUtil.getPublicData(ApiEndPoint.TOPIC);
        return result.Data;
    },
    // Post
    getPosts: async(pageable: PageableRequest): Promise<ResponseData> => {
        const params = new URLSearchParams(pageable as any).toString();
        const result = await ApiUtil.getPublicData(`${ApiEndPoint.POST}/all?${params}`);
        return result;
    },
    getPostDetail: async(postId: string | null): Promise<Post> => {
        const result = await ApiUtil.getPublicData(`${ApiEndPoint.POST}/${postId}`);
        return result.Data;
    },
    createPost: async({ newPost }: { newPost: 
        { 
            topics: Topic[], 
            header: string, 
            content: string, 
            postImage: PostImage[] 
        } 
    }) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}`, newPost);
        return result;
    },
    updatePost: async({ postId, newPost }: { postId: string, newPost: 
        { 
            topics: Topic[], 
            header: string, 
            content: string, 
            postImage: PostImage[] 
        } 
    }) => {
        const result = await ApiUtil.putData(`${ApiEndPoint.POST}/${postId}`, newPost);
        return result;
    },
    deletePost: async(postId: string) => {
        const result = await ApiUtil.deleteData(`${ApiEndPoint.POST}/${postId}`);
        return result;
    },
    likePost: async(postId: string) =>{
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}/${postId}/react?reactionType=${ReactionType.LIKE}`, {});
        return result;
    },
    dislikePost: async(postId: string) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}/${postId}/react?reactionType=${ReactionType.DISLIKE}`, {});
        return result;
    },
    // Comment
    // getComments: async() => {

    // },
    createComment: async({ postId, newComment } : { postId: string, newComment: 
        { 
            content: string
        } 
    }) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}/${postId}/comment`, newComment);
        return result;
    },
    updateComment: async({ postId, commentId, newComment } : { postId: string, commentId: string, newComment: 
        {
            content: string
        } 
    }) => {
        const result = await ApiUtil.putData(`${ApiEndPoint.POST}/${postId}/comment/${commentId}`, newComment);
        return result;
    },
    deleteComment: async(commentId: string) => {
        const result = await ApiUtil.deleteData(`${ApiEndPoint.POST}/comment/${commentId}`);
        return result;
    },
    likeComment: async(commentId: string) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}/comment/${commentId}/react?reactionType=${ReactionType.LIKE}`, {});
        return result;
    },
    dislikeComment: async(commentId: string) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.POST}/comment/${commentId}/react?reactionType=${ReactionType.DISLIKE}`, {});
        return result;
    }
}