"use client";

import { useEffect } from "react";
import { useSearchParams } from "next/navigation";

export default function OAuth2RedirectPage() {
    const searchParams = useSearchParams();

    useEffect(() => {
        const token = searchParams.get("token");

        if (!token) {
            window.close();
            return;
        }

        // Lấy thông tin từ URL
        const user = {
            id: searchParams.get("userId") || "",
            username: decodeURIComponent(searchParams.get("username") || ""),
            email: decodeURIComponent(searchParams.get("email") || ""),
            role: searchParams.get("role") || "USER",
            avatar: decodeURIComponent(searchParams.get("avatar") || ""),
            gender: searchParams.get("gender") || "UNKNOWN",
            phone: searchParams.get("phone") || "",
            address: decodeURIComponent(searchParams.get("address") || ""),
            achievement: searchParams.get("achievement") || "BEGINNER",
            totalSubmissionPoint: parseInt(searchParams.get("totalSubmissionPoint") || "0"),
            status: searchParams.get("status") || "ACTIVE",
            authProvider: searchParams.get("authProvider") || "GOOGLE",
            devices: JSON.parse(decodeURIComponent(searchParams.get("devices") || "[]")),
        };

        // Gửi dữ liệu về main window
        if (window.opener) {
            window.opener.postMessage({ token, user }, window.location.origin);
        }

        // Đóng popup sau khi gửi dữ liệu
        window.close();
    }, [searchParams]);

    return <p>Đang xử lý đăng nhập...</p>;
}
