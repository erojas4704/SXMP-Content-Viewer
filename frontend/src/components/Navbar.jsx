import "./css/Nav.css";
import NavButton from "./NavButton";
import InlineSearchForm from "./InlineSearchForm";
import { Link } from "react-router-dom";

const Navbar = () => {
  const userIsLoggedIn = false;
  return (
    <div className="nav">
      <Link to={{pathname: "/"}}><NavButton>SXMP</NavButton></Link>
      <div style={{ flexGrow: 1 }} />
      <InlineSearchForm />
      {!userIsLoggedIn && (
        <>
          <Link to={{pathname: "/login"}}><NavButton>Log In</NavButton></Link>
          <Link to={{pathname: "/register"}}><NavButton>Register</NavButton></Link>
        </>
      )}
    </div>
  );
};

export default Navbar;
