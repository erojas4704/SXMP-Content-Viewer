import { useEffect, useRef } from "react";
import gsap from "gsap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";

const ContentDescription = ({ title, description, expanded, hover }) => {
  const contentRef = useRef();
  const caretRef = useRef();

  useEffect(() => {
    if (expanded) {
      contentRef.current.style.display = "block";
      gsap.to(contentRef.current, { height: "245px", ease: "expo.out", opacity: 1});
    } else gsap.to(contentRef.current, { height: "0px", display: "none", ease: "expo.out", opacity: 0 });
  }, [expanded]);

  useEffect(() => {
    if (hover || expanded) {
      caretRef.current.style.display = "block";
      caretRef.current.style.opacity = 1;
      gsap.to(caretRef.current, { height: "10px", ease: "expo.out" });
    } else {
      gsap.to(caretRef.current, {
        ease: "expo.out",
        height: "0px",
        opacity: 0,
      });
    }
  }, [hover, expanded]);

  return (
    <div className="content-container" style={{ overflow: "hidden" }}>
      <div className="content-title">{title}</div>

      <div
        className="content-extra"
        ref={contentRef}
        style={{ display: "none" }}
      >
        {description}
      </div>
      <div className="content-expander" ref={caretRef}>
        <FontAwesomeIcon icon={expanded ? faCaretDown : faCaretUp} />
      </div>
    </div>
  );
};

export default ContentDescription;
