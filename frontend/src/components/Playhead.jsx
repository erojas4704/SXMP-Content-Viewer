import { useDispatch } from "react-redux";
import { play } from "../redux/content/contentSlice";
import "./css/Playhead.css";
import PlayButton from "./PlayButton";
import Timeline from "./Timeline";

const Playhead = (props) => {
  const { content } = props;
  const dispatch = useDispatch();

  const onPlayBtn = () => {
    console.log("play");
    dispatch(play(content));
  };
  return (
    <div {...props} className={"playhead " + (props.className || "")}>
      <PlayButton onClick={onPlayBtn} />
      <Timeline />
    </div>
  );
};

export default Playhead;
