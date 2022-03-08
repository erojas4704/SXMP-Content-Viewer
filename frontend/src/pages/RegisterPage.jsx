import { useEffect, useState } from "react";
import { Alert, Button, Col, Form, Row, Spinner } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../redux/auth/authSlice";
import "./css/FormPage.css";

const RegisterPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [handle, setHandle] = useState("");
  const { status, error, isAuthenticated } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) navigate("/");
  }, [isAuthenticated, navigate]);

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(register({ email, password, handle, confirmPassword }));
  };

  return (
    <div className="form-container h-100">
      <Row
        md={6}
        className="form-page align-items-center justify-content-center"
      >
        <Col md={6} className="center">
          <h2>Register for SXMP</h2>
          {error && <Alert variant="danger">{error.message}</Alert>}

          <Form.Group controlId="name">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Nickname"
              value={handle}
              onChange={(e) => setHandle(e.target.value)}
            />
          </Form.Group>
          <Form style={{ marginTop: "1rem" }} onSubmit={handleSubmit}>
            <Form.Group controlId="email">
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <Form.Text className="text-muted">
                This is only used for logging in.
              </Form.Text>
            </Form.Group>
            <Form.Group style={{ marginTop: "1rem" }} controlId="password">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="on"
              />
            </Form.Group>
            <Form.Group
              style={{ marginTop: "1rem" }}
              controlId="confirmPassword"
            >
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                autoComplete="on"
              />
            </Form.Group>
            {status === "pending" ? (
              <Spinner style={{ marginTop: "1rem" }} animation="border" />
            ) : (
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
            )}
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
