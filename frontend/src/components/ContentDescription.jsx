import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowUpRightFromSquare,
  faCaretDown,
  faCaretUp,
} from "@fortawesome/free-solid-svg-icons";

const ContentDescription = ({
  id,
  title,
  description,
  expanded,
  hover,
  onClick,
  onContentPageClick,
}) => {
  const mouseOverClass = hover || expanded ? "hover" : "";
  const expandedClass = expanded ? "expanded" : "";

  return (
    <div
      className="content-container"
      style={{ overflow: "hidden" }}
      onClick={onClick}
    >
      <div className="content-title">
        <div>{title}</div>
        {expanded && (
            <FontAwesomeIcon
              onClick={() => onContentPageClick(id)}
              className="content-profile-link"
              icon={faArrowUpRightFromSquare}
              style={{
                padding: "4px",
              }}
            />
        )}
      </div>

      <div className={`content-extra ${expandedClass}`}>
        <div>{description}</div>
      </div>
      <div className={`content-expander ${mouseOverClass}`}>
        <FontAwesomeIcon icon={expanded ? faCaretDown : faCaretUp} />
      </div>
    </div>
  );
};

export default ContentDescription;
