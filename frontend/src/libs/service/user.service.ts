import { ApiEndPoint } from "../constant/constant";
import { Post, User, UserLogin, UserProfile, UserRegist } from "../constant/types";
import { ApiUtil } from "../utils/apiUtil";

export const UserService = {
    // Auth
    login: async({ email, password} : UserLogin) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.AUTH}/login`, {email, password})
        if (!(result.Status == '200')) {
            throw new Error(`${result.Message}`);
        }
        localStorage.setItem('accessToken', result.Data.token)
        return result;
    },
    logout: async() => {
        const result = await ApiUtil.postData(`${ApiEndPoint.AUTH}/logout`, {})
        localStorage.removeItem('accessToken')
        return result
    },
    register: async({email, username, password} : UserRegist) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.AUTH}/register`, {email, username, password})
        if (!(result.Status == '200')) {
            throw new Error(`${result.Message}`);
        }
        return result;
    },
    // Profile
    getPersonalProfile: async() => {
        if(localStorage.getItem('accessToken')) {
            const result = await ApiUtil.getData(`${ApiEndPoint.AUTH}/profile`);
            return result.Data;
        } else {
            return null;
        }
    },
    updatePersonalProfile: async({ username, avatar, gender, phone, address, password } : UserProfile) => {
        const result = await ApiUtil.putData(`${ApiEndPoint.AUTH}/profile/update`, 
            { username, avatar, gender, phone, address, password }
        );
        return result;
    },
    getPersonalPosts: async(userId: string | undefined | null): Promise<Post[]> => {
        const result = await ApiUtil.getData(`${ApiEndPoint.USER}/${userId}/posts`)
        return result.Data;
    },
    // User info
    getUserProfile: async(userId: string | null): Promise<User> => {
        const result = await ApiUtil.getData(`${ApiEndPoint.USER}/${userId}/profile`)
        return result.Data;
    },
    getUserRanking: async(): Promise<User[]> => {
        const result = await ApiUtil.getData(`${ApiEndPoint.USER}/ranking`)
        return result.Data;
    }
}