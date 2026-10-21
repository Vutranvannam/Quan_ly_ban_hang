import { useEffect, useState } from "react";

import { getRoleListWithDeletedAtIsNullService }
from "../../../services/roleService";

export default function useRoles() {

    const [roles, setRoles] = useState([]);

    useEffect(() => {

        const fetchRoles = async () => {

            try {

                const result =
                    await getRoleListWithDeletedAtIsNullService();

                const roleOptions = result.map(role => ({
                    value: role.roleId,
                    label: role.name
                }));

                setRoles(roleOptions);

            } catch (err) {
                console.log(err);
            }
        };

        fetchRoles();

    }, []);

    return {
        roles,
    };
}