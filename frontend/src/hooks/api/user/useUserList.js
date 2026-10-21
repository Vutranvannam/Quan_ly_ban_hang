import { useEffect, useState } from "react";

import { getUserListService } from "../../../services/userService";

import useAbortController from "../../utils/useAbortController";

export default function useUserList(page, size, keyword, onSuccess) {

    const [users, setUsers] = useState([]);

    const { createSignal } = useAbortController();

    const fetchUsers = async () => {

        try {

            const signal = createSignal();

            const res = await getUserListService(
                page,
                size,
                keyword,
                signal
            );

            setUsers(res.content);

            onSuccess(res);

        } catch (err) {

            if (err.name !== "CanceledError") {
                console.log(err);
            }
        }
    };

    useEffect(() => {
        fetchUsers();
    }, [page, size, keyword]);

    return {
        users,
        fetchUsers,
    };
}