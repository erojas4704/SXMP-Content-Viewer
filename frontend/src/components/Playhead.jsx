import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";

const Playhead = (props) => {

  return (
    <div {...props} className={"playhead " + props.className}>
      <PlayButton />
      <Timeline />
    </div>
  );
};

export default Playhead;
