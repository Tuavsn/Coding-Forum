'use client'

import Loading from "@/components/common/Loading"
import { AuthContext } from "@/context/AuthContextProvider"
import { register } from "@/libs/actions/user.actions"
import { isValidEmail } from "@/libs/utils"
import { message } from "antd"
import { useRouter } from "next/navigation"
import { useContext, useEffect, useState } from "react"
import { useMutation } from "react-query"

export default function RegisterPage() {
    const {auth, setAuth} = useContext(AuthContext)

    const router = useRouter()

    const [isLoading, setIsloading] = useState(true)

    const [email, setEmail] = useState<string>('')

    const [username, setUsername] = useState<string>('')

    const [password, setPassword] = useState<string>('')

    const handleSetEmail = (e:React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
    }

    const handleSetUsername = (e:React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value)
    }

    const handleSetPassword = (e:React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
    }

    const registerMutation = useMutation(() => register({email: email, username: username, password: password}), {
        onMutate: () => {
            setIsloading(true)
        },

        onSuccess: (data) => {
            message.success(data.Message)
            router.push('/login')
            setIsloading(false)
        },

        onError: (error) => {
            setIsloading(false)
            if (error instanceof Error) {
                message.error(error.message);
            } else {
                message.error('Có lỗi xảy ra');
            }
        },
    })

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault()
        if(isValidEmail(email)) {
            registerMutation.mutate()
        } else {
            message.error("Email không hợp lệ")
        }
    }

    // Login route guard
    useEffect(() => {
        if(auth) {
            router.push('/home')
        } else {
            setIsloading(false)
        }
    }, [])

    if(isLoading) {
        return (<Loading />)
    }
    return <>
        <div className="max-w-lg mx-auto my-10 bg-white p-8 rounded-xl shadow shadow-slate-300">
            <h1 className="text-center text-4xl font-medium">Đăng ký</h1>
            <form action="" className="my-10">
                <div className="flex flex-col space-y-5">
                    <label htmlFor="email">
                        <p className="font-medium text-slate-700 pb-2">Email</p>
                        <input id="email" name="email" type="email" className="w-full py-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow" placeholder="Nhập email" onChange={handleSetEmail}/>
                    </label>
                    <label htmlFor="username">
                        <p className="font-medium text-slate-700 pb-2">Tên tài khoản</p>
                        <input id="username" name="username" type="username" className="w-full py-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow" placeholder="Nhập tên tài khoản" onChange={handleSetUsername}/>
                    </label>
                    <label htmlFor="password">
                        <p className="font-medium text-slate-700 pb-2">Mật khẩu</p>
                        <input id="password" name="password" type="password" className="w-full py-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow" placeholder="Nhập mật khẩu" onChange={handleSetPassword}/>
                    </label>
                    <button className="w-full py-3 font-medium text-white bg-indigo-600 hover:bg-indigo-500 rounded-lg border-indigo-500 hover:shadow inline-flex space-x-2 items-center justify-center" onClick={handleRegister}>
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                        </svg>
                        <span>Đăng Ký</span>
                    </button>
                    <p className="text-center">Bạn đã đăng ký tài khoản? <a href="login" className="text-indigo-600 font-medium inline-flex space-x-1 items-center"><span>Đăng nhập ngay </span><span><svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
                    </svg></span></a></p>
                </div>
            </form>
        </div>
    </>
}