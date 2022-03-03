import { faPause, faPlay } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./css/PlayButton.css";
const PlayButton = ({ isPlaying, onClick }) => {
  return (
    <button className="play-btn" onClick={onClick}>
      {isPlaying ? (
        <FontAwesomeIcon icon={faPause} />
      ) : (
        <FontAwesomeIcon icon={faPlay} />
      )}
    </button>
  );
};

export default PlayButton;
