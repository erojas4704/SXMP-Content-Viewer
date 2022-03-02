import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";

const Playhead = ({}) => {
  return (
    <div className="playhead">
      <div className="playhead-bar">
          <PlayButton />
          <Timeline />
      </div>
    </div>
  );
};

export default Playhead;
