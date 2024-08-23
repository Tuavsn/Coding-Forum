import { getData } from "@/utils/FetchData";

export const getTopic = async(): Promise<Topic[]> => {
    const token = sessionStorage.getItem('userToken');
    const result = await getData('api/topic', token ? token : '' );
    return result.Data;
}

export const getPostDetail = async(postId: string | null): Promise<Post> => {
    const token = sessionStorage.getItem('userToken');
    const result = await getData(`api/post/${postId}`, token ? token : '' );
    return result.Data;
}