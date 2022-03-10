import "./css/Nav.css";
import NavButton from "./NavButton";
import InlineSearchForm from "./InlineSearchForm";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../redux/auth/authSlice";
import {
  searchForContent,
  setSearchField,
  setSortBy,
} from "../redux/content/contentSlice";
import { useEffect } from "react";
import { Dropdown, DropdownButton } from "react-bootstrap";
import NavRadioGroup from "./NavRadioGroup";
import NavRadioButton from "./NavRadioButton";

const Navbar = () => {
  const { isAuthenticated } = useSelector((state) => state.auth);
  const { searchField, sortBy } = useSelector((state) => state.content);
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  const onSearch = (query) => {
    dispatch(searchForContent(query));
    dispatch(setSearchField(query));
  };

  useEffect(() => {
    setSearchField("");
  }, [dispatch]);

  const handleSearchChange = (query) => {
    //We only want to dispatch the action if the search field is emptied by the user.
    if (query.length === 0) dispatch(setSearchField(query));
  };

  const handleFilterChange = (value) => {
    dispatch(setSortBy(value));
  };

  return (
    <div className="nav">
      <Link to={{ pathname: "/" }}>
        <NavButton className="nav-logo">SXMP</NavButton>
      </Link>
      <div
        style={{
          marginLeft: "2rem",
          marginRight: "1rem",
          display: "flex",
          alignItems: "center",
        }}
      >
        <span>Sort by: </span>
      </div>
      <NavRadioGroup
        defaultValue={sortBy}
        nullable={true}
        onChange={handleFilterChange}
      >
        <NavRadioButton value="title">Name</NavRadioButton>
        <NavRadioButton value="author">Author</NavRadioButton>
      </NavRadioGroup>

      <div style={{ flexGrow: 1 }} />
      <InlineSearchForm
        defaultValue={searchField}
        onChange={handleSearchChange}
        onSubmit={onSearch}
      />
      {!isAuthenticated ? (
        <>
          <Link to={{ pathname: "/login" }}>
            <NavButton>Log In</NavButton>
          </Link>
          <Link to={{ pathname: "/register" }}>
            <NavButton>Register</NavButton>
          </Link>
        </>
      ) : (
        <>
          <Link to={{ pathname: "/playlist" }}>
            <NavButton>Playlist</NavButton>
          </Link>
          <NavButton onClick={handleLogout}>Log Out</NavButton>
        </>
      )}
    </div>
  );
};

export default Navbar;
