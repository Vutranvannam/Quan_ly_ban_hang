import HomeLayout from "../layouts/HomeLayout";
import GreetingCard from "../components/dashboard/GreetingCard";
import StatsSection from "../components/dashboard/stats/StatsSection";
import TopProducts from "../components/dashboard/top/TopProducts";
import BestItems from "../components/dashboard/best/BesItems";

function DashBoardPage() {
    return (
        <HomeLayout>
            <div className="content-page">
                <div className="container-fluid">
                    <div className="row">
                        <GreetingCard name="Phúc" time="buổi sáng tốt lành" />
                        <StatsSection />
                        <TopProducts />
                        <BestItems />
                        
                        

                    </div> 
                </div>
            </div>        
        </HomeLayout>
    );
}

export default DashBoardPage;