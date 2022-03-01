import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { getAllContent } from "../reducers/contentSlice";
import ContentPreview from "./ContentPreview";
import "./css/Content.css";

const ContentPage = () => {
  const dispatch = useDispatch();
  const content = useSelector((state) => state.content);

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
      {content.content.map((content) => {
        return (
          <ContentPreview key={content.id} content={content}></ContentPreview>
        );
      })}
    </div>
  );
};

export default ContentPage;
