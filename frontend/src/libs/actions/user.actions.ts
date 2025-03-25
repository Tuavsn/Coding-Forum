import { getData, postData, putData } from "@/utils/FetchData"
import { Post, User, UserLogin, UserProfile, UserRegist } from "../types";

export const login = async ({ email, password} : UserLogin) => {
    const result = await postData('api/v1/auth/login', {email, password})
    if (!(result.Status == '200')) {
        throw new Error(`${result.Message}`);
    }
    localStorage.setItem('accessToken', result.Data.token)
    return result;
}

export const register = async({email, username, password} : UserRegist) => {
    const result = await postData('api/v1/auth/register', {email, username, password})
    if (!(result.Status == '200')) {
        throw new Error(`${result.Message}`);
    }
    return result;
}

export const logout = async () => {
    const result = await postData('api/v1/auth/logout', {})
    localStorage.removeItem('accessToken')
    return result
}

export const getInfo = async () => {
    if(localStorage.getItem('accessToken')) {
        const result = await getData('api/v1/auth/profile');
        return result.Data;
    } else {
        return null;
    }
}

export const updateProfile = async ({username, avatar, gender, phone, address, password} : UserProfile) => {
    const result = await putData('api/v1/auth/profile/update', {username, avatar, gender, phone, address, password});
    return result;
}


export const getPersonalPosts = async(userId: string | undefined | null): Promise<Post[]> => {
    const result = await getData(`api/v1/user/${userId}/posts`)
    return result.Data;
}

export const getUserProfile = async(userId: string | null): Promise<User> => {
    const result = await getData(`api/v1/user/${userId}/profile`)
    return result.Data;
}

export const getUserRanking = async(): Promise<User[]> => {
    const result = await getData(`api/v1/user/ranking`)
    return result.Data;
}