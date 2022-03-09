import React, { useState } from "react";
import "./css/ToggleIconButton.css";

const ToggleIconButton = (props) => {
  const { onClick, isToggled, icon, colorToggled, size } = props;
  const iconToggled = props.iconToggled || icon;
  const iconHover = props.iconHover || icon;

  const [isHovered, setIsHovered] = useState(false);
  const iconToRender = isToggled ? iconToggled : isHovered ? iconHover : icon;

  return (
    <div
      className="toggle-icon-button"
      onMouseOver={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={onClick}
      style={{
        color: isHovered || isToggled ? colorToggled : "",
      }}
    >
      {React.cloneElement(iconToRender, { size })}
    </div>
  );
};

export default ToggleIconButton;
