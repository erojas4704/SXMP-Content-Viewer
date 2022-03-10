import { useEffect, useState } from "react";

const colors = [
  "#34495e",
  "#2c3e50",
  "#e74c3c",
  "#e67e22",
  "#f1c40f",
  "#f39c12",
  "#1abc9c",
  "#3498db",
  "#8e44ad",
  "#27ae60",
  "#7f8c8d",
  "#95a5a6",
];

const useRandomColor = () => {
  const [color, setColor] = useState("#000000");
  useEffect(() => {
    const i = Math.floor(Math.random() * colors.length);
    setColor(colors[i]);
  }, [setColor]);

  return color;
};

export default useRandomColor;
