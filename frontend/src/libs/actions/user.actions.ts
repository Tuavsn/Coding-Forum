import { getData, postData } from "@/utils/FetchData"

export const login = async ({ email, password} : UserLogin) => {
    const result = await postData('api/auth/login', {email, password})
    sessionStorage.setItem('username', result.Data.username)
    sessionStorage.setItem('userToken', result.Data.token)
    return result.Data;
}

export const logout = async () => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await postData('api/auth/logout', {}, token)
    sessionStorage.removeItem('userToken')
    sessionStorage.removeItem('username')
    return result.Data
}

export const getInfo = async () => {
    const token = sessionStorage.getItem('userToken') || undefined;
    const result = await getData('api/auth/info', token );
    return result.Data;
}

// export const getUserInfo = async 

