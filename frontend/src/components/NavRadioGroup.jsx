import React, { useState } from "react";
import { v4 as uuid } from "uuid";

const NavRadioGroup = (props) => {
  const { defaultValue, nullable, children, onChange } = props;
  const [value, setValue] = useState(defaultValue);
  const onClick = (newValue) => {
    if (nullable && value === newValue) {
      setValue(null);
      onChange(null);
    } else {
      setValue(newValue);
      onChange(newValue);
    }
  };

  return (
    <>
      {children.map((child) =>
        React.cloneElement(child, {
          selectedValue: value,
          onClick,
          key: uuid(),
        })
      )}
    </>
  );
};

export default NavRadioGroup;
