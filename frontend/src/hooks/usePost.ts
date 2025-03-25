import { AuthContext } from "@/context/AuthContextProvider";
import { createPost, deletePost, dislikePost, likeComment, likePost, updatePost } from "@/libs/actions/post.acttion";
import { ReactionType } from "@/libs/enum";
import { Post, PostImage, Topic } from "@/libs/types";
import { stringToSlug } from "@/libs/utils";
import { message, UploadFile } from "antd";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { useMutation } from "react-query";

interface UsePostProps {
    post?: Post;
    isPostDetail?: boolean;
}

interface PostContent {
    id: string;
    header: string;
    content: string;
    images: PostImage[];
    previewImages: string;
    imageFiles: UploadFile[];
    topic: Topic[];
}

export default function usePost(props: UsePostProps) {

    const { post, isPostDetail } = props ?? {};

    const { auth } = useContext(AuthContext);

    const router = useRouter();

    const [isLiked, setIsLiked] = useState(false);

    const [isDisliked, setIsDisliked] = useState(false);

    const [isCommented, setIsCommented] = useState(false);

    const [likesCount, setLikesCount] = useState(0);

    const [disLikesCount, setDisLikesCount] = useState(0);

    const [commentsCount, setCommentsCount] = useState(0);

    const [postCreateLoading, setPostCreateLoading] = useState(false);

    const [postUpdateLoading, setPostUpdateLoading] = useState(false);

    const [postDeleteLoading, setPostDeleteLoading] = useState(false);

    const [isOpenModal, setIsOpenModal] = useState(false);

    const [postContent, setPostContent] = useState<PostContent>({
        id: '',
        header: '',
        content: '',
        images: [],
        previewImages: '',
        imageFiles: [],
        topic: []
    });

    /**
     * Update the specific field of post content (header, content, images)
     * 
     * @param field {header | content | images}
     * @param value {value}
     */
    const handleInputChange = <K extends keyof PostContent> (field: K, value: PostContent[K]) => {
        setPostContent((prevState) => ({
            ...prevState,
            [field]: value
        }))
    }

    // Handle Like
    const handleLike = (id: string) => {
        auth ? postLikeMutation.mutate(id) 
        : message.error("Bạn chưa thực hiện đăng nhập");
    }

    /**
     * Post Like Mutation
     */
    const postLikeMutation = useMutation(likePost, {
        onSuccess: (data) => {
            router.refresh();
        }
    })

    // Handle Dislike
    const handleDislike = (id: string) => {
        auth ? isDisliked ? message.error("Bạn đã Dislike rồi!!!") 
        : postDislikeMutation.mutate(id) 
        : message.error("Bạn chưa thực hiện đăng nhập");
    }

    /**
     * Post Dislike Mutation
     */
    const postDislikeMutation = useMutation(dislikePost, {
        onSuccess: (data) => {
            router.refresh();
        }
    })

    // Handle Create
    const handleCreate = () => {
        // if (validateForm()) {
            postCreateMutation.mutate({
                newPost: {
                    topic: postContent.topic,
                    header: postContent.header,
                    content: postContent.content,
                    postImage: postContent.images
                }
            })
        // }
    }

    /**
     * Post Create Mutation
     */
    const postCreateMutation = useMutation(createPost, {
        onMutate: () => {
            setPostCreateLoading(true);
        },

        onSuccess: (data) => {
            setPostCreateLoading(false);

            setIsOpenModal(false);

            // queryClient.invalidateQueries('getPostDetail');
            
            resetModal();

            message.success(data.Message);

            router.refresh();
        },

        onError: (error: any) => {
            setPostCreateLoading(false);
            message.error(error.message);
        }
    })

    // Handle Update
    const handleUpdate = () => {
        // if (validateForm()) {
            postUpdateMutation.mutate({
                postId: postContent.id,
                newPost: {
                    header: postContent.header,
                    content: postContent.content,
                    postImage: postContent.images
                }
            })
        // }
    }

    /**
     * Post Update Mutation
     */
    const postUpdateMutation = useMutation(updatePost, {
        onMutate: () => {
            setPostUpdateLoading(true);
        },

        onSuccess: (data) => {
            setPostUpdateLoading(false);

            setIsOpenModal(false);

            // queryClient.invalidateQueries('getPostDetail');
            
            resetModal();

            message.success(data.Message);

            router.refresh();
        },

        onError: (error: any) => {
            setPostUpdateLoading(false);
            message.error(error.message);
        }
    })

    // Handle Delete
    const handleDelete = () => {
        post ? postDeleteMutation.mutate(post.id) 
        : message.error("Không có bài viết để xoá");
    }
    

    /**
     * Post Delete Mutation
     */
    const postDeleteMutation = useMutation(deletePost, {
        onMutate: () => {
            setPostDeleteLoading(true);
        },

        onSuccess: (data) => {
            setPostDeleteLoading(false);

            // queryClient.invalidateQueries('getPostDetail');

            message.success(data.Message);

            isPostDetail ?? router.push('/home');

            router.refresh();
        },

        onError: (error: any) => {
            setPostDeleteLoading(false);
            message.error(error.message);
        }
    })

    // Others

    /**
     * Toggle Modal
     */
    const toggleModal = (type: 'create' | 'update' | 'close') => {
        switch (type) {
            case 'create':
                handleOpenCreateModal();
                break;
            case 'update':
                handleOpenUpdateModal();
                break;
            default:
                handleCloseModal();
        }
    }

    /**
     * Handle Comment
     */
    const handleComment = () => {
        post ? router.push(`/post/${stringToSlug(post.header)}?id=${post.id}`)
        : message.error("Không có bài viết để bình luận");
    }

    /**
     * Open Create Modal
     */
    const handleOpenCreateModal = () => {
        setIsOpenModal(true);
    }

    /**
     * Open Update Modal
     */
    const handleOpenUpdateModal = () => {
        post ? (setPostContent({
                id: post.id,
                header: post.header,
                content: post.content,
                images: post.postImage,
                previewImages: '',
                imageFiles: [],
                topic: post.topic
            }),
            setIsOpenModal(true)
        ) : message.error("Không có bài viết để chỉnh sửa");
    }

    /**
     * Close Modal
     */
    const handleCloseModal = () => {
        resetModal();
        
        setIsOpenModal(false)
    }

    /**
     * Reset Post Modal Content
     */
    const resetModal = () => {
        setPostContent({
            id: '',
            header: '',
            content: '',
            images: [],
            previewImages: '',
            imageFiles: [],
            topic: []
        })
    }

    /**
     * Validate Modal fields
     */
    const validateForm = () => {
    }

    /**
     * Count likes
     */
    const countReactionsAndComments = () => {
        if (!post) return;
        setLikesCount(
            post.postReactions.filter(
                (reaction) => reaction.reactionType === ReactionType.LIKE
            ).length
        );
        setDisLikesCount(
            post.postReactions.filter(
                (reaction) => reaction.reactionType === ReactionType.DISLIKE
            ).length
        );
        setCommentsCount(post.postComment.length);
    }

    /**
     * Check if user (reacted & commented) post or not
     */
    const checkUserReactionAndComment = () => {
        if (!post) return;
        setIsLiked(
            post.postReactions.some(
                (reaction) => reaction.reactionType === ReactionType.LIKE && reaction.user.id === auth?.id
            )
        );
        setIsDisliked(
            post.postReactions.some(
                (reaction) => reaction.reactionType === ReactionType.DISLIKE && reaction.user.id === auth?.id
            )
        );
        setIsCommented(
            post.postComment.some(
                (comment) => comment.user.id === auth?.id
            )
        );
    }

    useEffect(() => {
        if (auth && post) {
            // First check
            countReactionsAndComments();
            checkUserReactionAndComment();
        }
    }, [auth?.id, post])

    return {
        isLiked,
        isDisliked,
        isCommented,
        likesCount,
        disLikesCount,
        commentsCount,
        postCreateLoading,
        postUpdateLoading,
        postDeleteLoading,
        handleLike,
        handleDislike,
        handleComment,
        handleCreate,
        handleUpdate,
        handleDelete,
        isOpenModal,
        toggleModal,
        handleInputChange,
        resetModal,
        postContent,
    }

}