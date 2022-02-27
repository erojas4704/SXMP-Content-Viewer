import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import Api from "../Api";
import { getAllContent } from "../reducers/contentSlice";

const ContentPage = () => {
  const dispatch = useDispatch();
  const content = useSelector((state) => state.content);

  useEffect(() => {
    dispatch(getAllContent());
    // Api.getAllContent();
  }, [dispatch]);

  return (
    <div>
      <h1>Content Page</h1>
      {content.content.map((c) => {
        return (
          <div key={c.id}>
            <h5>{c.title}</h5>
            <div>{c.description}</div>
          </div>
        );
      })}
    </div>
  );
};

export default ContentPage;
