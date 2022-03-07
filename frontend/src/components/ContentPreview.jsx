import { useContext, useEffect, useState } from "react";
import ContentDescription from "./ContentDescription";
import Playhead from "./Playhead";
import NowPlayingContext from "../contexts/NowPlayingContext";

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
  //TODO rename to ContentPlayer
  const [expanded, setExpanded] = useState(false);
  const [hover, setHover] = useState(false);
  const [audio, setAudio] = useState(null);
  const [status, setStatus] = useState("idle");

  const { toggleAudio } = useContext(NowPlayingContext);

  const onMetadata = (e) => {
    setStatus("fulfilled");
  };

  useEffect(() => {
    if (!audio && expanded) {
      const audio = new Audio(content.audioURL);
      setAudio(audio);
      audio.addEventListener("loadedmetadata", onMetadata);
      setStatus("pending");

      return () => {
        //On dismount, remove event listeners.
        audio.removeEventListener("loadedmetadata", onMetadata);
      };
    }
  }, [expanded]);

  return (
    <div
      className="content-preview"
      onMouseEnter={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
      style={{
        backgroundImage: `url(${content.imageURL})`,
        backgroundPosition: "center",
        backgroundColor: `${content.imageURL ? null : getRandomColor()}`,
      }}
    >
      <div className="content-head" />
      <ContentDescription
        title={content.title}
        description={content.description}
        expanded={expanded}
        onClick={() => setExpanded(!expanded)}
        hover={hover}
      />
      {expanded && (
        <Playhead
          content={content}
          onToggle={() => toggleAudio(audio, content)}
          audio={audio}
        />
      )}
    </div>
  );
};

export default ContentPreview;
