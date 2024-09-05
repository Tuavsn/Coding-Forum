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
    const result = await postData('api/auth/logout', {})
    sessionStorage.removeItem('userToken')
    return result
}

export const getInfo = async () => {
    const result = await getData('api/auth/profile');
    return result.Data;
}
