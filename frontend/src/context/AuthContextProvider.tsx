import { User } from "@/libs/types";
import { createContext } from "react";

export const AuthContext = createContext<{
    auth: User | null,
    setAuth: (value: User | null) => void;
}>({
    auth: null,
    setAuth: function(value: User | null) {
        return value;
    }
})