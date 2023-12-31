import NavButton from "./NavButton";
import "./css/NavRadioButton.css";

const NavRadioButton = ({ children, value, selectedValue, onClick, ...props }) => {
  const enabled = (value === selectedValue);
  return (
    <NavButton
      style={{
        padding: "0",
        marginLeft: "8px",
        marginRight: "8px",
        position: "relative",
        height: "100%",
        paddingLeft: "1rem",
        paddingRight: "1rem",
        lineHeight: "56px"
      }}
      onClick={() => onClick(value)}
    >
      {children}
      {enabled && (
        <>
          <div className="nav-gradient-underlay" />
          <div className="nav-radio-bottom" />
        </>
      )}
    </NavButton>
  );
};

export default NavRadioButton;
