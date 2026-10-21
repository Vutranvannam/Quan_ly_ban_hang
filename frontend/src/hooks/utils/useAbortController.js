import { useRef } from "react";

export default function useAbortController() {

    const controllerRef = useRef(null);

    const createSignal = () => {

        if (controllerRef.current) {
            controllerRef.current.abort();
        }

        const controller = new AbortController();

        controllerRef.current = controller;

        return controller.signal;
    };

    return {
        createSignal,
    };
}