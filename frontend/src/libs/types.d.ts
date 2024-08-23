// User enum

declare enum Gender {
    MALE = 'Nam',
    FEMALE = 'Nữ'
}

declare enum Achievement {
    BEGINNER = 'Beginner',
    INTERMEDIATE = 'Intermediate',
    EXPERT = 'Expert'
}

declare enum AccountStatus {
    ACTIVE = 'Active',
    INACTIVE = 'Inactive',
    BLOCK = 'Block',
}

declare enum AccountProvider {
    LOCAL = 'Local',
    GOOGLE = 'Google',
    GITHUB = 'Github'
}

// Message enum

declare enum MessageStatus {
    RECEIVED = 'Đã nhận',
    READ = 'Đã xem',
    HIDE = 'Ẩn'
}

// Notify enum

declare enum NotifyStatus {
    READ = 'Đã xem',
    UNREAD = 'Chưa xem'
}

// Post enum

declare enum PostStatus {
    ATIVE = 'Đang hoạt động',
    CLOSED = 'Đã đóng'
}

// Problem enum

declare enum ProblemResult {
    ACCEPT = 'Accept',
    WRONG_ANSWER = 'Wrong answer',
    TIME_LIMIT = 'Time limit',
    STACK_OVERFLOW = 'Stack overflow'
}

declare enum ProblemSubmissionLanguageType {
    C = '1',
    CPLUSPLUS = '2',
    PYTHON = '3',
    JAVA = '4'
}

declare enum ProblemType {
    EASY = 'Easy',
    MEDIUM = 'Medium',
    HARD = 'Hard'
}

declare enum PostReactionType {
    LIKE = 'Thích',
    LOVE = 'Thả tim',
    SMILE = 'Thả haha',
    DISLIKE = 'Không thích'
}

declare type Device = {
    info: string;
    ip: string;
    expireAt: Date;
    lastLoginTime: Date;
}

declare type User = {
    username: string;
    role: string;
    email: string;
    avatar: string;
    gender: Gender;
    phone: string;
    address: string;
    achievement: Achievement;
    status: AccountStatus;
    authProvider: AccountProvider;
    devices: Device[];
}

declare type UserLogin = {
    email: string;
    password: string;
}

declare type Topic = {
    id: string;
    createdBy: Date;
    updatedBy: Date;
    createdAt: Date;
    updatedAt: Date;
    name: string;
    posts: Post[]
}

declare type PostImage = {
    image: string;
}

declare type PostComment = {
    user: User;
    content: string;
}

declare type Post = {
    id: string;
    createdBy: Date;
    updatedBy: Date;
    createdAt: Date;
    updatedAt: Date;
    user: User;
    header: string;
    content: string;
    status: PostStatus;
    postImage: PostImage[];
    postComment: [];
    postReactions: [];
}