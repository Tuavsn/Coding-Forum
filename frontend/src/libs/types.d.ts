declare type Device = {
    info: string;
    ip: string;
    expireAt: Date;
    lastLoginTime: Date;
}

declare type User = {
    id: string;
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

declare type UserRegist = {
    email: string;
    username: string;
    password: string; 
}

declare type UserProfile = {
    username: string;
    avatar: string;
    gender: Gender;
    phone: string;
    address: string;
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
    postComment: PostComment[];
    postReactions: PostReaction[];
}

declare type PostComment = {
    id: string;
    createdBy: Date;
    updatedBy: Date;
    createdAt: Date;
    updatedAt: Date;
    user: User;
    content: string;
    commentReactions: CommentReaction[];
}

declare type CommentReaction = {
    id: string;
    createdBy: Date;
    updatedBy: Date;
    createdAt: Date;
    updatedAt: Date;
    user: User;
    reactionType: ReactionType;
}

declare type PostReaction = {
    id: string;
    createdBy: Date;
    updatedBy: Date;
    createdAt: Date;
    updatedAt: Date;
    user: User;
    reactionType: ReactionType;
}

import { GetProp, UploadProps } from "antd";
import Password from "antd/es/input/Password";
import { AccountProvider, AccountStatus, Achievement, Gender, PostStatus, ReactionType } from "./enum";
declare type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];
