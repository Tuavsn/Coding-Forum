'use client'

import { login } from "@/libs/actions/user.actions"
import { message } from "antd"
import { useRouter } from "next/navigation"
import { useEffect, useState } from "react"
import { useMutation, useQueryClient } from "react-query"

export default function LoginPage() {

    const queryClient = useQueryClient()

    const router = useRouter()

    const [email, setEmail] = useState<string>('')

    const [password, setPassword] = useState<string>('')

    const [messageApi, contextHolder] = message.useMessage();

    const handleSetEmail = (e:React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
    }

    const handleSetPassword = (e:React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
    }

    const isValidEmail = (email: string) => {
        // Biểu thức chính quy kiểm tra định dạng email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    const mutation = useMutation(() => login({email: email, password: password}), {
        onSuccess: () => {
            queryClient.invalidateQueries('userInfo')
            router.push('/home')
        }
    })

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()
        if(isValidEmail(email)) {
            mutation.mutate()
        } else {
            messageApi.open({
                type: 'error',
                content: 'Email không hợp lệ',
                duration: 5,
            });
        }
    }

    useEffect(() => {
        const token = sessionStorage.getItem('userToken')
        if(token != null) {
            router.push('/home')
        }
    }, [])

    return <>
        {contextHolder}
        <div className="max-w-lg mx-auto my-10 bg-white p-8 rounded-xl shadow shadow-slate-300">
            <h1 className="text-center text-4xl font-medium">Đăng nhập</h1>
            <div className="my-5">
                <button className="w-full text-center py-3 my-3 border flex space-x-2 items-center justify-center border-slate-200 rounded-lg text-slate-700 hover:border-slate-400 hover:text-slate-900 hover:shadow transition duration-150">
                    <img src="https://www.svgrepo.com/show/355037/google.svg" className="w-6 h-6" alt="" /> <span>Đăng nhập với Google</span>
                </button>
            </div>
            <form action="" className="my-10">
                <div className="flex flex-col space-y-5">
                    <label htmlFor="email">
                        <p className="font-medium text-slate-700 pb-2">Email</p>
                        <input id="email" name="email" type="email" className="w-full py-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow" placeholder="Nhập email" onChange={handleSetEmail}/>
                    </label>
                    <label htmlFor="password">
                        <p className="font-medium text-slate-700 pb-2">Mật khẩu</p>
                        <input id="password" name="password" type="password" className="w-full py-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow" placeholder="Nhập mật khẩu" onChange={handleSetPassword}/>
                    </label>
                    <div className="flex flex-row justify-between">
                        <div>
                            <a href="#" className="font-medium text-indigo-600">Quên mật khẩu?</a>
                        </div>
                    </div>
                    <button className="w-full py-3 font-medium text-white bg-indigo-600 hover:bg-indigo-500 rounded-lg border-indigo-500 hover:shadow inline-flex space-x-2 items-center justify-center" onClick={handleLogin}>
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                        </svg>
                        <span>Đăng nhập</span>
                    </button>
                    <p className="text-center">Bạn chưa đăng ký tài khoản? <a href="#" className="text-indigo-600 font-medium inline-flex space-x-1 items-center"><span>Đăng ký ngay </span><span><svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
                    </svg></span></a></p>
                </div>
            </form>
        </div>
    </>
}