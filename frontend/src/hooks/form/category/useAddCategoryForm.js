import { useState } from "react";

export default function useAddCategoryForm() {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const resetForm = () => {
        setName("");
        setDescription("");
    };

    return {
        name,
        setName,

        description,
        setDescription,

        resetForm
    }
}