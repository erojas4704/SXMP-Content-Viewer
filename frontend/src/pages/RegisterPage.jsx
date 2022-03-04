import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import "./css/FormPage.css";

const RegisterPage = () => {
  return (
    <div className="form-container h-100">
      <Row
        md={6}
        className="form-page align-items-center justify-content-center"
      >
        <Col md={6} className="center">
          <h2>Register for SXMP</h2>
          <Form style={{ marginTop: "1rem" }}>
            <Form.Group controlId="email">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
            </Form.Group>
            <Form.Group style={{ marginTop: "1rem" }} controlId="password">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" placeholder="Password" />
            </Form.Group>
            <Form.Group
              style={{ marginTop: "1rem" }}
              controlId="confirmPassword"
            >
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control type="password" placeholder="Password" />
            </Form.Group>
            <div style={{ marginTop: "2rem" }}>
              <Button
                style={{ marginRight: "8px" }}
                variant="primary"
                type="submit"
              >
                Submit
              </Button>
              <Link to="/login">
                <Button variant="success">I already have an account</Button>
              </Link>
            </div>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
