import "./css/Timeline.css";
import moment from "moment";

const fromSecondsToMS = (time) => {
  if (time === undefined || isNaN(time)) return "--:--";
  const parsedTime = moment.utc(time * 1000).format("mm:ss");
  return parsedTime;
};

const Timeline = ({ currentTime, duration, onScrub }) => {
  const progressPercent = duration !== 0 ? (currentTime / duration) * 100 : 0;

  const handleScrub = (e) => {
    const rect = e.target.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const scrubTime = (x / rect.width) * duration;

    onScrub(scrubTime);
  };

  return (
    <div className="timeline">
      <div className="timeline-time">
        {fromSecondsToMS(currentTime)} / {fromSecondsToMS(duration)}
      </div>
      <div className="timeline-bar-container" onClick={handleScrub}>
        <div
          className="timeline-bar current"
          style={{ width: `${progressPercent}%` }}
        />
        <div className="timeline-bar" />
      </div>
    </div>
  );
};

export default Timeline;
