import { useEffect, useState } from "react";
import {
  HandThumbsDown,
  HandThumbsUp,
  Star,
  StarFill,
  StarHalf,
} from "react-bootstrap-icons";
import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";
import ToggleIconButton from "./ToggleIconButton";

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

      <ToggleIconButton
        icon={<HandThumbsUp />}
        colorToggled="#3498db"
        size={22}
      />
      <ToggleIconButton
        icon={<HandThumbsDown />}
        colorToggled="#e74c3c"
        size={22}
      />
      <ToggleIconButton
        icon={<Star />}
        iconToggled={<StarFill />}
        iconHover={<StarHalf />}
        colorToggled="#f1c40f"
        size={22}
      />
    </div>
  );
};

export default Playhead;
