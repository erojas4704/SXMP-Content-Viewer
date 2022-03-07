import { useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { login } from "../redux/auth/authSlice";
import "./css/FormPage.css";

const LoginPage = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { status, user } = useSelector((state) => state.auth);

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(login({ email, password }));
  };

  return (
    <div className="form-container h-100">
      <Row
        md={6}
        className="form-page align-items-center justify-content-center"
      >
        <Col md={6} className="center">
          <h2>Welcome back</h2>
          <Form style={{ marginTop: "1rem" }} onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                value={email}
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
              />
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
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
