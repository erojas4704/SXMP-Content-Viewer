import { useEffect } from "react";
import { Container, Placeholder } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { getAllContent, getContentArray } from "../redux/content/contentSlice";
import ContentPreview from "./ContentPreview";
import "./css/Content.css";
import { v4 as uuid } from "uuid";

const ContentPage = () => {
  const dispatch = useDispatch();
  const content = useSelector((state) => getContentArray(state));
  const search = useSelector((state) => getContentArray(state, true));
  const { status } = useSelector((state) => state.content);

  const contentArray = search ? search : content;

  useEffect(() => {
    dispatch(getAllContent());
  }, [dispatch]);

  const body =
    status !== "pending" ? (
      contentArray.map((data) => {
        return <ContentPreview key={data.id} content={data}></ContentPreview>;
      })
    ) : (
      <Container fluid className="d-flex flex-wrap">
        {new Array(18).fill(0).map(() => {
          return (
            <Placeholder
              as="div"
              key={uuid()}
              variant="light"
              className="content-preview placeholder-wave"
            />
          );
        })}
      </Container>
    );

  return (
    <div className="content-page" style={{ display: "flex", flexWrap: "wrap" }}>
      {body}
    </div>
  );
};

export default ContentPage;
