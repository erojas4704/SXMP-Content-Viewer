import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";

const ContentDescription = ({ title, description, expanded, hover }) => {

  // useEffect(() => {
  //   if (expanded) {
  //     contentRef.current.style.display = "block";
  //     gsap.to(contentRef.current, { height: "235px", ease: "expo.out", opacity: 1});
  //   } else gsap.to(contentRef.current, { height: "0px", display: "none", ease: "expo.out", opacity: 0 });
  // }, [expanded]);

  // useEffect(() => {
  //   if (hover || expanded) {
  //     caretRef.current.style.display = "block";
  //     caretRef.current.style.opacity = 1;
  //     gsap.to(caretRef.current, { height: "10px", ease: "expo.out" });
  //   } else {
  //     gsap.to(caretRef.current, {
  //       ease: "expo.out",
  //       height: "0px",
  //       opacity: 0,
  //     });
  //   }
  // }, [hover, expanded]);

  const mouseOverClass = hover || expanded ? "hover" : "";
  const expandedClass = expanded ? "expanded" : "";

  return (
    <div className="content-container" style={{ overflow: "hidden" }}>
      <div className="content-title">{title}</div>

      <div className={`content-extra ${expandedClass}`}>
        {description}
      </div>
      <div className={`content-expander ${mouseOverClass}`}>
        <FontAwesomeIcon icon={expanded ? faCaretDown : faCaretUp} />
      </div>
    </div>
  );
};

export default ContentDescription;
