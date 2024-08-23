import { getData, postData } from "@/utils/FetchData"

export const login = async ({ email, password} : UserLogin) => {
    const result = await postData('api/auth/login', {email, password})
    sessionStorage.setItem('username', result.Data.username)
    sessionStorage.setItem('userToken', result.Data.token)
    return result.Data;
}

export const logout = async () => {
    const token = sessionStorage.getItem('userToken');
    if(token) {
        const result = await postData('api/auth/logout', {}, token ? token : '')
        sessionStorage.removeItem('userToken')
        sessionStorage.removeItem('username')
        return result.Data
    } else {
        return null;
    }
}

export const getInfo = async () => {
    const token = sessionStorage.getItem('userToken');
    if(token) {
        const result = await getData('api/auth/info', token ? token : '' );
        return result.Data;
    } else {
        return null;
    }
}

// export const getUserInfo = async 

