import { useState } from "react";
import ContentDescription from "./ContentDescription";
import Playhead from "./Playhead";
const placeholderColors = [
  "#34495e",
  "#2c3e50",
  "#e74c3c",
  "#e67e22",
  "#f1c40f",
  "#f39c12",
  "#1abc9c",
  "#3498db",
];

const getRandomColor = () => {
  const i = Math.floor(Math.random() * placeholderColors.length);
  return placeholderColors[i];
};

const ContentPreview = ({ content }) => {
  const [expanded, setExpanded] = useState(false);
  const [hover, setHover] = useState(false);

  const backgroundStyle = {
    backgroundImage: `url(${content.imageURL})`,
    backgroundPosition: "center",
    backgroundColor: `${content.imageURL ? null : getRandomColor()}`,
  };

  const handleClick = () => {
    setExpanded(!expanded);
  };

  const handleMouseEnter = () => {
    setHover(true);
  };

  const handleMouseLeave = () => {
    setHover(false);
  };

  return (
    <div
      className="content-preview"
      style={backgroundStyle}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <div className="content-head" />
      <ContentDescription
        title={content.title}
        description={content.description}
        expanded={expanded}
        onClick={handleClick}
        hover={hover}
      />
      {expanded && <Playhead content={content} />}
    </div>
  );
};

export default ContentPreview;
