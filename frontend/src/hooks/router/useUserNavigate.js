import { useNavigate } from "react-router-dom";

export default function useUserNavigate() {
    const navigate = useNavigate();
    
    const goToUpdateUser = (userId) => {
        navigate(`/user/update/${userId}`);
    }

    const goToAddUser = () => {
        navigate("/user/add");
    }

    return {
        goToAddUser,
        goToUpdateUser,
    };
}