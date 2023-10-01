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
import {
  Container,
  Dropdown,
  DropdownButton,
  Nav,
  Navbar,
} from "react-bootstrap";
import NavRadioGroup from "./NavRadioGroup";
import NavRadioButton from "./NavRadioButton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlusCircle } from "@fortawesome/free-solid-svg-icons";

const SXMPNavbar = () => {
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
    <Navbar bg="dark" expand="lg" collapseOnSelect className="p-0 " style={{position: "sticky", top: '0', zIndex: '9999'}}>
      <Navbar.Brand>
        <Link to={{ pathname: "/" }}>
          <NavButton className="nav-logo">SXMP</NavButton>
        </Link>
      </Navbar.Brand>
      <Link style={{position: "absolute", left: '50%', transform: "translate(-50%)"}} to={{ pathname: "/content/create" }}>
        <NavButton>
          <FontAwesomeIcon size="lg" icon={faPlusCircle} />
        </NavButton>
      </Link>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" className="navbar-dark" />
      <Navbar.Collapse>
        <div
          style={{
            marginLeft: "2rem",
            marginRight: "1rem",
            display: "flex",
            color: "white",
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
        <div
          style={{
            flexGrow: 1,
            display: "flex",
            alignItems: "center",
            flexDirection: "row",
          }}
        ></div>
        <Nav className="mr-auto">
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
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default SXMPNavbar;
