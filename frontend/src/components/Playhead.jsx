import { useEffect, useState } from "react";
import {
  HandThumbsDown,
  HandThumbsDownFill,
  HandThumbsUp,
  HandThumbsUpFill,
  Star,
  StarFill,
  StarHalf,
} from "react-bootstrap-icons";
import { useDispatch } from "react-redux";
import { reactToContent } from "../redux/content/contentSlice";
import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";
import ToggleIconButton from "./ToggleIconButton";

const Playhead = (props) => {
  const { onToggle, audio, content } = props;
  const isPlaying = audio && !audio.paused;
  const { currentTime, duration } = audio || {};
  const [seconds, setSeconds] = useState(currentTime);
  const dispatch = useDispatch();

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
        isToggled={content?.rating === 1}
        icon={<HandThumbsUp />}
        iconToggled={<HandThumbsUpFill />}
        colorToggled="#3498db"
        size={22}
        onClick={() =>
          dispatch(reactToContent({ contentId: content.id, rating: 1 }))
        }
      />
      <span className="playhead-text likes">{content?.likes || 0}</span>
      <ToggleIconButton
        isToggled={content?.rating === -1}
        icon={<HandThumbsDown />}
        iconToggled={<HandThumbsDownFill />}
        colorToggled="#e74c3c"
        size={22}
        onClick={() =>
          dispatch(reactToContent({ contentId: content.id, rating: -1 }))
        }
      />
      <span className="playhead-text dislikes">{content?.dislikes || 0}</span>
      <ToggleIconButton
        isToggled={content?.isFavorite}
        icon={<Star />}
        iconToggled={<StarFill />}
        iconHover={<StarHalf />}
        colorToggled="#f1c40f"
        size={22}
        onClick={() =>
          dispatch(
            reactToContent({
              contentId: content.id,
              isFavorite: !content.isFavorite,
            })
          )
        }
      />
    </div>
  );
};

export default Playhead;
