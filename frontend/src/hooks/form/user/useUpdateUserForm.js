import { useState } from "react";

import { formatDate } from "../../../utils/date";

export default function useUpdateUserForm() {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [fullName, setFullName] = useState("");
    const [selectedRoles, setSelectedRoles] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState(null);
    const [locked, setLocked] = useState(false);
    const [createdAt, setCreatedAt] = useState("");
    const [updatedAt, setUpdatedAt] = useState("");

    const fillForm = (user, roleOptions) => {

        setUsername(user.username);

        const selected = roleOptions
            .filter(r => user.roles.includes(r.label))
            .map(r => r.value);

        setEmail(user.email);
        setFullName(user.fullName);
        setSelectedRoles(selected);
        setSelectedStatus(user.enabled);
        setLocked(user.locked);
        setCreatedAt(formatDate(user.createdAt));
        setUpdatedAt(formatDate(user.updatedAt));
    };

    return {

        username,
        setUsername,

        email,
        setEmail,

        fullName,
        setFullName,

        selectedRoles,
        setSelectedRoles,

        selectedStatus,
        setSelectedStatus,

        locked,
        setLocked,

        createdAt,
        updatedAt,

        fillForm,
    };
}