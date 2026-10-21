import { exportExcelService } from "../../../services/categoryService";

import useAbortController from "../../utils/useAbortController";

export default function useExportCategoryExcel(keyword) {
    const { createSignal } = useAbortController();
    const exportCategories = async () => {
        try {
            const signal = createSignal();
            await exportExcelService(keyword, signal);
        } catch (err) {
            if (err.name !== "CanceledError") {
                console.log(err);
            }
        }
    }
    return { exportCategories };
}