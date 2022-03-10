import "./css/ContentProfile.css";
import { Col, Container, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { deleteContent, getContent } from "../redux/content/contentSlice";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTrash } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { Modal, Button, Alert } from "react-bootstrap";

const ContentProfile = () => {
  const { id } = useParams();
  const [showModal, setShowModal] = useState(false);
  const content = useSelector((state) => getContent(state, id));
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleClose = () => {
    setShowModal(false);
  };

  const handleShow = () => {
    setShowModal(true);
  };

  const handleDelete = () => {
    dispatch(deleteContent(id));
  };

  if (!content) {
    return (
      <Container fluid className="m-4" style={{ color: "white" }}>
        <h2>404 - Content not found</h2>
      </Container>
    );
  }

  return (
    <Container
      fluid
      className="p-4 content-profile-container d-flex flex-grow-1 flex-column"
    >
      {content.status === "error" && (
        <Alert variant="danger">{content.error.message}</Alert>
      )}
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Content?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete "{content.title}"?
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant={"danger"}
            onClick={() => {
              handleDelete();
              handleClose();
            }}
          >
            Delete
          </Button>
          <Button onClick={handleClose}>Cancel</Button>
        </Modal.Footer>
      </Modal>
      <Row className="d-flex flex-grow-1">
        <Col>
          <h2>{content.title}</h2>
          <p>{content.description}</p>
        </Col>
        <Col className="d-flex flex-column justify-content-start">
          <div
            className="content-profile-image"
            style={{
              backgroundImage: `url('${content.imageUrl}')`,
            }}
          ></div>
          {/* <Playhead content={content} /> */}
          <div>
            <FontAwesomeIcon
              className="content-profile-control"
              size={"xl"}
              icon={faEdit}
              color="white"
              onClick={() => navigate(`/content/${id}/update`)}
            />
            <FontAwesomeIcon
              className="content-profile-control trash"
              size={"xl"}
              icon={faTrash}
              color="white"
              onClick={() => setShowModal(true)}
            />
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default ContentProfile;
