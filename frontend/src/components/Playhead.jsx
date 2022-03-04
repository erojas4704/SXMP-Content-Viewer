import {
  faSquarePlus,
  faThumbsDown,
  faThumbsUp,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";

const Playhead = (props) => {
  const { onToggle, audio } = props;
  const isPlaying = audio && !audio.paused;
  const { currentTime, duration } = audio || {};
  const [seconds, setSeconds] = useState(currentTime);

  useEffect(() => {
    if (audio) setSeconds(audio.currentTime);
  }, [audio]);

  useEffect(() => {
    if (isPlaying) {
      const intervalId = setInterval(() => {
        setSeconds((seconds) => seconds + 1);
      }, 1000);

      return () => {
        clearInterval(intervalId);
      };
    }
  }, [isPlaying]);

  const onScrub = (scrubTime) => {
    if (audio) audio.currentTime = scrubTime;
    setSeconds(scrubTime);
  };

  return (
    <div {...props} className={"playhead " + (props.className || "")}>
      <PlayButton onClick={onToggle} isPlaying={isPlaying} />
      <Timeline currentTime={seconds} duration={duration} onScrub={onScrub} />
      {/* I don't like these thumb icons */}
      {/* <FontAwesomeIcon
        style={{ paddingLeft: "5px", paddingRight: "5px" }}
        icon={faThumbsUp}
        color={"white"}
        size={"xl"}
      />
      <FontAwesomeIcon
        style={{ paddingLeft: "5px", paddingRight: "5px" }}
        icon={faThumbsDown}
        color={"white"}
        size={"xl"}
      />*/}
      <FontAwesomeIcon
        style={{ paddingLeft: "5px", paddingRight: "5px" }}
        icon={faSquarePlus}
        color={"white"}
        size={"xl"}
      /> 
    </div>
  );
};

export default Playhead;
