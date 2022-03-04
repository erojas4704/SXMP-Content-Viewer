import "./css/Marquee.css";

const Marquee = ({ text, show }) => {
  return <div className={`marquee ${show && "show"}`}>{text}</div>;
};

export default Marquee;
