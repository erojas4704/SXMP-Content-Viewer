import ContentDescription from "./ContentDescription";
const placeholderColors = [
  "#34495e",
  "#2c3e50",
  "#e74c3c",
  "#e67e22",
  "#f1c40f",
  "#f39c12",
  "#1abc9c",
  "#3498db",
];

const getRandomColor = () => {
  const i = Math.floor(Math.random() * placeholderColors.length);
  return placeholderColors[i];
};

const ContentPreview = ({ content }) => {
  const backgroundStyle = {
    backgroundImage: `url(${content.imageURL})`,
    backgroundColor: `${content.imageURL ? null : getRandomColor()}`,
  };

  return (
    <>
      <div className="content-preview" style={backgroundStyle}>
        <div className="content-head" />
        <ContentDescription
          title={content.title}
          description={content.description}
        />
      </div>
    </>
  );
};

export default ContentPreview;
