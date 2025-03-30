"use client";

import { useEffect } from "react";
import { useSearchParams } from "next/navigation";

// Component con chứa logic sử dụng useSearchParams
function OAuth2RedirectContent() {
  const searchParams = useSearchParams();

  useEffect(() => {
    const token = searchParams.get("token");

    if (!token) {
      window.close();
      return;
    }

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

    if (window.opener) {
      window.opener.postMessage({ token, user }, window.location.origin);
    }
    window.close();
  }, [searchParams]);

  return <p>Đang xử lý đăng nhập...</p>;
}

// Component trang được bọc bởi Suspense
import { Suspense } from "react";

export default function OAuth2RedirectPage() {
  return (
    <Suspense fallback={<p>Loading...</p>}>
      <OAuth2RedirectContent />
    </Suspense>
  );
}
