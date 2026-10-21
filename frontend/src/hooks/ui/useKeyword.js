import { useState } from "react";

export default function useKeyword(defaultKeyword = "") {

    const [keyword, setKeyword] = useState(defaultKeyword);

    return {
        keyword,
        setKeyword,
    };
}