import "./css/NavButton.css";

const NavButton = (props) => {
  return <button {...props} className="nav-button">{props.children}</button>;
};

export default NavButton;
