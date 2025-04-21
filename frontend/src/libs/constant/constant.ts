const API_END_POINT = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const API_PREFIX = process.env.NEXT_PUBLIC_API_PREFIX || 'api/v1';

export const ApiEndPoint = {
    AUTH: `${API_END_POINT}/${API_PREFIX}/auth`,
    USER: `${API_END_POINT}/${API_PREFIX}/user`,
    POST: `${API_END_POINT}/${API_PREFIX}/post`,
    TOPIC: `${API_END_POINT}/${API_PREFIX}/topic`
    PROBLEM: `${API_END_POINT}/${API_PREFIX}/problem`,
}