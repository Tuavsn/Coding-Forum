'use client'

import { useState } from "react"
import { QueryClient, QueryClientProvider } from "react-query"
import { AuthContext } from "./AuthContextProvider"
import { User } from "@/libs/types"

export default function ReactQueryProvider({
    children
} : {
    children: React.ReactNode
}) {
    
    const [client] = useState(new QueryClient({
        defaultOptions: {
            queries: {
                refetchOnWindowFocus: false
            }
        }
    }))

    const [auth, setAuth] = useState<User | null>(null)

    const value = {
        auth,
        setAuth
    }

    return (
        <QueryClientProvider client={client}>
            <AuthContext.Provider value={value}>
                {children}
            </AuthContext.Provider>
        </QueryClientProvider>
    )
}