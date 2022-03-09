import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { getAllContent, getContent } from "../redux/content/contentSlice";
import ContentPreview from "./ContentPreview";
import "./css/Content.css";

const ContentPage = () => {
  const dispatch = useDispatch();
  const content = useSelector((state) => getContent(state));

  useEffect(() => {
    dispatch(getAllContent());
  }, [dispatch]);

  return (
    <div
      style={{
        display: "flex",
        flexWrap: "wrap",
      }}
    >
      {content.map((data) => {
        return <ContentPreview key={data.id} content={data}></ContentPreview>;
      })}
    </div>
  );
};

export default ContentPage;
