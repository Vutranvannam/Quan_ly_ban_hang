export default function DefaultRow({children, onUpdate, onDelete, activeAction=true}) {
    return (
        <tr>

            {children}
            
            <td>
                <div className="d-flex align-items-center list-action">
                    {activeAction && 
                        <>          
                            <button
                                className="badge bg-success mr-2" 
                                data-toggle="tooltip" 
                                data-placement="top" 
                                title="" 
                                data-original-title="Edit"
                                href="#"
                                onClick={onUpdate}
                            >
                                <i className="ri-pencil-line mr-0"></i>
                            </button>
                            <button
                                className="badge bg-warning mr-2" 
                                data-toggle="tooltip" 
                                data-placement="top" 
                                title="" 
                                data-original-title="Delete"
                                href="#"
                                onClick={onDelete}
                            >
                                <i className="ri-delete-bin-line mr-0"></i>
                            </button>
                        </>
                    }
                    
                </div>
            </td>
        </tr>
    );
}