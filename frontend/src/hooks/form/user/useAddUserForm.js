import { useState } from "react";

export default function useUserForm() {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [fullName, setFullName] = useState("");

    const [selectedRoles, setSelectedRoles] = useState([]);

    const [selectedStatus, setSelectedStatus] = useState(true);

    const resetForm = () => {
        setUsername("");
        setEmail("");
        setFullName("");
        setSelectedRoles([]);
        setSelectedStatus(true);
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

        resetForm,
    };
}