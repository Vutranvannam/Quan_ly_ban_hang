import React from "react";

const AuthMessage = ({ title, note, titleClass, noteClass }) => {
  return (
    <>
      <h2 className={titleClass}>{title}</h2>
      <p className={noteClass}>{note}</p>
    </>
  );
};

export default AuthMessage;