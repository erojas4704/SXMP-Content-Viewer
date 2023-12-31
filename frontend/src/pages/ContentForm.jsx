import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { Container, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  createContent,
  getContent,
  updateContent,
} from "../redux/content/contentSlice";
import "./css/FormPage.css";

const ContentForm = (props) => {
  const { edit } = props;
  const { id } = useParams();
  const content = useSelector((state) => getContent(state, id));
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [contentObj, setContentObj] = useState({
    title: "",
    description: "",
    audioUrl: "",
    imageUrl: "",
    name: "",
    status: "",
  });

  const onSubmit = (e) => {
    e.preventDefault();
    if (edit) {
      dispatch(updateContent({ contentId: id, content: contentObj }));
    } else {
      dispatch(createContent(contentObj));
    }
  };

  useEffect(() => {
    if (edit && content) {
      setContentObj({ ...content });
    }
  }, [edit, content]);

  useEffect(() => {
    if (content && content.status === "fulfilled") {
      navigate(`/content/${content.id}`);
    }
  }, [content, navigate]);

  return (
    <Container fluid className="form-container  white">
      <Form
        className="spaced-form"
        style={{ marginTop: "2rem", color: "white" }}
      >
        <h2 style={{ marginBottom: "2rem" }}>
          {edit ? "Update" : "Create"} Content
        </h2>
        <Form.Group controlId="title">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Title"
            value={contentObj.title || ""}
            name="title"
            onChange={(e) =>
              setContentObj({ ...contentObj, [e.target.name]: e.target.value })
            }
          />
        </Form.Group>
        <Form.Group controlId="description">
          <Form.Label>Description</Form.Label>
          <Form.Control
            as="textarea"
            rows="3"
            placeholder="Description"
            name="description"
            value={contentObj.description || ""}
            onChange={(e) =>
              setContentObj({ ...contentObj, [e.target.name]: e.target.value })
            }
          />
        </Form.Group>
        <Form.Group controlId="audioUrl">
          <Form.Label>Audio URL</Form.Label>
          <Form.Control
            type="text"
            placeholder="Example: https://audio.com/audio.mp3"
            value={contentObj.audioUrl || ""}
            name="audioUrl"
            onChange={(e) =>
              setContentObj({ ...contentObj, [e.target.name]: e.target.value })
            }
          />
        </Form.Group>
        <Form.Group controlId="imageUrl">
          <Form.Label>Image URL</Form.Label>
          <Form.Control
            name="imageUrl"
            type="text"
            placeholder="Example: https://image.com/image.png"
            value={contentObj.imageUrl || ""}
            onChange={(e) =>
              setContentObj({ ...contentObj, [e.target.name]: e.target.value })
            }
          />
        </Form.Group>
        <Form.Group controlId="name">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            placeholder="Name"
            value={contentObj.name || ""}
            onChange={(e) =>
              setContentObj({ ...contentObj, [e.target.name]: e.target.value })
            }
          />
        </Form.Group>
        <Button variant="primary" type="submit" onClick={onSubmit}>
          Submit
        </Button>
      </Form>
    </Container>
  );
};

export default ContentForm;
