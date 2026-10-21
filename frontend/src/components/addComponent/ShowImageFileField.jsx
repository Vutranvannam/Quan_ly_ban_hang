export default function ShowImageFileField({
    label,
    imageUrl,
    onChange
}) {

    return (
        <div className="col-md-12">

            <div className="form-group">

                <label>{label}</label>

                <input
                    type="file"
                    className="form-control image-file"
                    accept="image/*"
                    onChange={(e) => onChange(e.target.files[0])}
                />

                {imageUrl && (
                    <div className="mt-3 text-center">

                        <img
                            src={imageUrl}
                            className="style-img img-fluid m-auto p-3" 
                            alt="image"
                        />

                    </div>
                )}

            </div>

        </div>
    );
}