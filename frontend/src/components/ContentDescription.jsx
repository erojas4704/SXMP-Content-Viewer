const ContentDescription = ({ title, description }) => {
  return (
    <div className="content-container">
      <div className="content-title">{title}</div>
      {/* <div className="content-description">{description}</div> */}
      <div className="content-expander">^</div>
    </div>
  );
};

export default ContentDescription;
