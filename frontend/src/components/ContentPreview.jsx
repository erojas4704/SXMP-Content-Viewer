import { useContext, useEffect, useState } from "react";
import ContentDescription from "./ContentDescription";
import Playhead from "./Playhead";
import NowPlayingContext from "../contexts/NowPlayingContext";
import useRandomColor from "../hooks/useRandomColor";


const ContentPreview = ({ content }) => {
  //TODO rename to ContentPlayer
  const [expanded, setExpanded] = useState(false);
  const [hover, setHover] = useState(false);
  const [audio, setAudio] = useState(null);
  const [, setStatus] = useState("idle");
  const { toggleAudio } = useContext(NowPlayingContext);
  const randomColor = useRandomColor();

  const onMetadata = (e) => {
    setStatus("fulfilled");
  };

  useEffect(() => {
    if (!audio && expanded) {
      const audio = new Audio(content.audioUrl);
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
        backgroundImage: `url(${content.imageUrl})`,
        backgroundPosition: "center",
        backgroundColor: `${content.imageUrl ? null : randomColor}`,
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
