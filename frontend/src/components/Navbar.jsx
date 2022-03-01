import "./css/Nav.css";
import NavButton from "./NavButton";
import InlineSearchForm from "./InlineSearchForm";

const Navbar = () => {
  const userIsLoggedIn = false;
  return (
    <div className="nav">

      <div style={{ flexGrow: 1 }} />
      <InlineSearchForm />
      {!userIsLoggedIn && (
        <>
          <NavButton>Log In</NavButton>
          <NavButton>Register</NavButton>
        </>
      )}
    </div>
  );
};

export default Navbar;
