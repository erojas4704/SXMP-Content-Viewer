import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";

const ContentDescription = ({ title, description, expanded, hover, onClick }) => {

  const mouseOverClass = hover || expanded ? "hover" : "";
  const expandedClass = expanded ? "expanded" : "";

  return (
    <div className="content-container" style={{ overflow: "hidden" }} onClick={onClick}>
      <div className="content-title">{title}</div>

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
