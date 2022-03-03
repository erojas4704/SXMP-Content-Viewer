import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";

const Playhead = ({}) => {
  return (
    <div className="playhead">
      <PlayButton />
      <Timeline />
    </div>
  );
};

export default Playhead;
