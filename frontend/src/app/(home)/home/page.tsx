import BreadCrumb from "@/components/common/BreadCrumb";
import HomeWelcomeCard from "@/components/home/HomeWelcomeCard";
import TopicList from "@/components/posts/TopicList";

export default function HomePage() {
    return (
        <div>
            <BreadCrumb type="post" />
            <HomeWelcomeCard />
            <TopicList />
        </div>
    )
}