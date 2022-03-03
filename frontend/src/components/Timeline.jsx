import "./css/Timeline.css";

const Timeline = ({ time, duration }) => {
  return (
    <div className="timeline">
      <div className="timeline-time">00:00 / 00:00</div>
      <div className="timeline-bar" />
    </div>
  );
};

export default Timeline;
