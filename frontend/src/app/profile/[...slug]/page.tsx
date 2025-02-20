import BreadCrumb from "@/components/common/BreadCrumb";
import PersonalProfile from "@/components/profile/PersonalProfile";

export default function ProfilePage() {
    return (
        <div>
            <BreadCrumb type="profile"/>
            <PersonalProfile />
        </div>
    )
}