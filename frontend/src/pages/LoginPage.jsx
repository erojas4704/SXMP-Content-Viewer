import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import "./css/FormPage.css";

const LoginPage = () => {
  return (
    <div className="form-container h-100">
      <Row
        md={6}
        className="form-page align-items-center justify-content-center"
      >
        <Col md={6} className="center">
          <h2>Welcome back</h2>
          <Form style={{ marginTop: "1rem" }}>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" placeholder="Password" />
            </Form.Group>
            <div style={{ marginTop: "2rem" }}>
              <Button
                style={{ marginRight: "8px" }}
                variant="primary"
                type="submit"
              >
                Login
              </Button>
              <Link to="/register">
                <Button variant="success">Create Account</Button>
              </Link>
            </div>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default LoginPage;
