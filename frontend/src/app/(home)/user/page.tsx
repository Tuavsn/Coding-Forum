import BreadCrumb from "@/components/common/breadcrumb/BreadCrumb";
import UserProfile from "@/components/profile/UserProfile";

export default function ProfilePage() {
    return (
        <div>
            <BreadCrumb type="profile"/>
            <UserProfile />
        </div>
    )
}