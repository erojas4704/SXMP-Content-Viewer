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

  return (
    <div {...props} className={"playhead " + (props.className || "")}>
      <PlayButton onClick={onToggle} isPlaying={isPlaying} />
      <Timeline currentTime={seconds} duration={duration} />
    </div>
  );
};

export default Playhead;
