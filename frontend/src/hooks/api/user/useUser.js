import { useEffect, useState } from "react";

import { getUserService } from "../../../services/userService";

export default function useUser(userId) {

    const [user, setUser] = useState(null);

    const fetchUser = async () => {

        try {

            const res = await getUserService(userId);

            setUser(res);

            return res;

        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchUser();
    }, [userId]);

    return {
        user,
        setUser,
        fetchUser,
    };
}