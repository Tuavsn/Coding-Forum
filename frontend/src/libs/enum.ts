// User enum

export const enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE'
}

export const enum Achievement {
    BEGINNER = 'BEGINNER',
    INTERMEDIATE = 'INTERMEDIATE',
    EXPERT = 'EXPERT'
}

export const enum AccountStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE',
    BLOCK = 'BLOCK',
}

export const enum AccountProvider {
    LOCAL = 'LOCAL',
    GOOGLE = 'GOOGLE',
    GITHUB = 'GITHUB'
}

// Message enum

export const enum MessageStatus {
    RECEIVED = 'RECEIVED',
    READ = 'READ',
    HIDE = 'HIDE'
}

// Notify enum

export const enum NotifyStatus {
    READ = 'READ',
    UNREAD = 'UNREAD'
}

// Post enum

export const enum PostStatus {
    ATIVE = 'ATIVE',
    CLOSED = 'CLOSED'
}

// Problem enum

export const enum ProblemResult {
    ACCEPT = 'ACCEPT',
    WRONG_ANSWER = 'WRONG ANSWER',
    TIME_LIMIT = 'TIME LIMIT',
    STACK_OVERFLOW = 'STACK OVERFLOW'
}

export const enum ProblemSubmissionLanguageType {
    C = '1',
    CPLUSPLUS = '2',
    PYTHON = '3',
    JAVA = '4'
}

export enum ProblemType {
    EASY = 'EASY',
    MEDIUM = 'MEDIUM',
    HARD = 'HARD'
}

export enum ReactionType {
    LIKE = 'LIKE',
    DISLIKE = 'DISLIKE'
}