'use client'

import { useState } from "react"
import { QueryClient, QueryClientProvider } from "react-query"

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

    return (
        <QueryClientProvider client={client}>
            {children}
        </QueryClientProvider>
    )
}