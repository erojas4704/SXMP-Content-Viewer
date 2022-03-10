import { useContext, useEffect, useState } from "react";
import ContentDescription from "./ContentDescription";
import Playhead from "./Playhead";
import NowPlayingContext from "../contexts/NowPlayingContext";
import useRandomColor from "../hooks/useRandomColor";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPodcast } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const ContentPreview = ({ content }) => {
  //TODO rename to ContentPlayer
  const [expanded, setExpanded] = useState(false);
  const [hover, setHover] = useState(false);
  const [audio, setAudio] = useState(null);
  const [, setStatus] = useState("idle");
  const { toggleAudio } = useContext(NowPlayingContext);
  const randomColor = useRandomColor();
  const navigate = useNavigate();

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

  const onContentPageClick = (id) => {
    navigate(`/content/${id}`);
  };

  return (
    <div
      className="content-preview"
      onMouseEnter={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
      style={{
        position: "relative",
        backgroundImage: `url(${content.imageUrl})`,
        backgroundPosition: "center",
        backgroundColor: `${content.imageUrl ? null : randomColor}`,
      }}
    >
      <div className="content-head" />
      <ContentDescription
        id={content.id}
        title={content.title}
        description={content.description}
        expanded={expanded}
        onClick={() => setExpanded(!expanded)}
        hover={hover}
        onContentPageClick={onContentPageClick}
      />
      {expanded && (
        <Playhead
          content={content}
          onToggle={() => toggleAudio(audio, content)}
          audio={audio}
        />
      )}
      {!content.imageUrl && (
        <FontAwesomeIcon
          style={{
            position: "absolute ",
            color: "#00",
            top: "50%",
            left: "50%",
            opacity: 0.5,
            transform: "translate(-50%, -50%)",
          }}
          size={"4x"}
          icon={faPodcast}
        />
      )}
    </div>
  );
};

export default ContentPreview;
