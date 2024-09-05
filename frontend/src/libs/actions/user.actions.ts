import { getData, postData } from "@/utils/FetchData"
import { UserLogin, UserRegist } from "../types";

export const login = async ({ email, password} : UserLogin) => {
    const result = await postData('api/auth/login', {email, password})
    if (!(result.Status == '200')) {
        throw new Error(`${result.Message}`);
    }
    sessionStorage.setItem('userToken', result.Data.token)
    return result;
}

export const register = async({email, username, password} : UserRegist) => {
    const result = await postData('api/auth/register', {email, username, password})
    if (!(result.Status == '200')) {
        throw new Error(`${result.Message}`);
    }
    return result;
}

export const logout = async () => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData('api/auth/logout', {}, token)
    sessionStorage.removeItem('userToken')
    return result
}

export const getInfo = async () => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData('api/auth/profile', token );
    return result.Data;
}

// export const getUserInfo = async 

