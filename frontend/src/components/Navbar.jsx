import "./css/Nav.css";
import NavButton from "./NavButton";
import InlineSearchForm from "./InlineSearchForm";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../redux/auth/authSlice";

const Navbar = () => {
  const { isAuthenticated } = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
    console.log("OUT");
  };

  return (
    <div className="nav">
      <Link to={{ pathname: "/" }}>
        <NavButton>SXMP</NavButton>
      </Link>
      <div style={{ flexGrow: 1 }} />
      <InlineSearchForm />
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
        <NavButton onClick={handleLogout}>Log Out</NavButton>
      )}
    </div>
  );
};

export default Navbar;
