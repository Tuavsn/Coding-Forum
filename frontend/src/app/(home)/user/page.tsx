import BreadCrumb from "@/components/common/BreadCrumb";
import Loading from "@/components/common/Loading";
import UserProfile from "@/components/profile/UserProfile";
import { Suspense } from "react";

export default function ProfilePage() {
    return (
        <div>
            <BreadCrumb type="profile"/>
            <Suspense fallback={<Loading />}>
                <UserProfile />
            </Suspense>
        </div>
    )
}