import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useRef, useState } from "react";
import "./css/InlineSearchForm.css";

const InlineSearchForm = ({ onSubmit }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const inputRef = useRef();

  const handleClick = (e) => {
    inputRef.current.focus();
    console.log("CLICKITY CLACKITY");
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(searchTerm);
  };

  const handleChange = (e) => {
    setSearchTerm(e.target.value);
  };

  return (
    <form className="inline-search-form" onSubmit={handleSubmit} onClick={handleClick}>
      <input
        ref={inputRef}
        className="nav-search-input"
        type="text"
        placeholder="Search"
        onChange={handleChange}
        value={searchTerm}
      />
      <FontAwesomeIcon
        icon={faMagnifyingGlass}
        style={{
          color: "white",
          position: "absolute",
          transform: "translate(-125%, 15%)",
          textAlign: "center",
        }}
      />
    </form>
  );
};

export default InlineSearchForm;
