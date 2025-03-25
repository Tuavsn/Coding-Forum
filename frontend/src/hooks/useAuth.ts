import { AuthContext } from "@/context/AuthContextProvider";
import { login, register } from "@/libs/actions/user.actions";
import { message } from "antd";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react"
import { useMutation } from "react-query";

interface AuthForm {
    email: string;
    username: string;
    password: string;
}

export default function useAuth() {
    
    const {auth, setAuth} = useContext(AuthContext)

    const router = useRouter()

    const [isLoading, setIsLoading] = useState(false)

    const [authForm, setAuthForm] = useState<AuthForm>({
        email: '',
        username: '',
        password: ''
    })

    const oauth2Url = `${process.env.NEXT_PUBLIC_API_URL}/oauth2/authorize/google` || 'http://localhost:8080/oauth2/authorize/google'

    /**
     * Update the specific field of Auth Form (email, username and password)
     * 
     * @param field {email | username | password}
     * @param value {value}
     */
    const handleInputChange = <K extends keyof AuthForm> (field: K, value: AuthForm[K]) => {
        setAuthForm((prevState) => ({
            ...prevState,
            [field]: value
        }))
    }

    // Handle Login
    const handleLoginWithGoogle = () => {
        const popup = window.open(oauth2Url, "_blank", "width=500,height=600");
    
        if (!popup) {
            alert("Không thể mở cửa sổ popup! Hãy kiểm tra cài đặt trình duyệt.");
            return;
        }
    
        const receiveMessage = (event: MessageEvent) => {
            if (event.origin !== window.location.origin) return;
    
            const { token, user } = event.data;
            if (token && user) {
                localStorage.setItem("accessToken", token);
                setAuth(user);
                popup.close();
                router.push("/home");
            }
    
            window.removeEventListener("message", receiveMessage);
        };
    
        window.addEventListener("message", receiveMessage);
    };    

    const handleLogin = () => {
        loginMutation.mutate({
            email: authForm.email,
            password: authForm.password
        })
    }

    /**
     * Login Mutation
     */
    const loginMutation = useMutation(login, {
        onMutate: () => {
            setIsLoading(true);
        },

        onSuccess: (data) => {
            setIsLoading(false);
            setAuth(data.Data);
            router.push("/home");
            message.success(data.message);
        },

        onError: (error: any) => {
            setIsLoading(false);
            message.error(error.message)
        }
    })

    // Handle Register
    const handleRegister = () => {
        registerMutation.mutate({
            email: authForm.email,
            username: authForm.username,
            password: authForm.password
        })
    }

    /**
     * Register Mutation
     */
    const registerMutation = useMutation(register, {
        onMutate: () => {
            setIsLoading(true);
        },

        onSuccess: (data) => {
            setIsLoading(false);
            router.push("/login")
            message.success(data.message);
        },

        onError: (error: any) => {
            setIsLoading(false);
            message.error(error.message)
        }
    })

    // Handle Forgot Password
    const handleForgotPassword = () => {
        
    }

    /**
     * Forgot Password Mutation
     */
    // const forgotPasswordMutation = useMutation()

    useEffect(() => {
        if (auth) {
            router.push("/home")
        } else {

        }
    }, [])

    return {
        isLoading,
        handleLogin,
        handleLoginWithGoogle,
        handleRegister,
        handleForgotPassword,
        handleInputChange,
        authForm
    }
}