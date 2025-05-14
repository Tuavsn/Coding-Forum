const SERVER_END_POINT = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const API_PREFIX = process.env.NEXT_PUBLIC_API_PREFIX || 'api/v1';

export const ApiEndPoint = {
    AUTH: `${SERVER_END_POINT}/${API_PREFIX}/auth`,
    USER: `${SERVER_END_POINT}/${API_PREFIX}/user`,
    POST: `${SERVER_END_POINT}/${API_PREFIX}/post`,
    TOPIC: `${SERVER_END_POINT}/${API_PREFIX}/topic`,
    PROBLEM: `${SERVER_END_POINT}/${API_PREFIX}/problem`,
}

export const OAUTH2URL = `${SERVER_END_POINT}/oauth2/authorize/google`