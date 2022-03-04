import "./css/Timeline.css";
import moment from "moment";

const fromSecondsToMS = (time) => {
  if (time === undefined) return "--:--";
  const parsedTime = moment.utc(time * 1000).format("mm:ss");
  return parsedTime;
};

const Timeline = ({ currentTime, duration }) => {
  return (
    <div className="timeline">
      <div className="timeline-time">
        {fromSecondsToMS(currentTime)} / {fromSecondsToMS(duration)}
      </div>
      <div className="timeline-bar" />
    </div>
  );
};

export default Timeline;
