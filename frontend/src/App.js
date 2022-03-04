import "./App.css";
import ContentPage from "./components/ContentPage";
import Navbar from "./components/Navbar";
import AudioPlayer from "./components/AudioPlayer";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import AboutPage from "./pages/AboutPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";

function App() {
  return (
    <div className="App">
      <div className="container">
        <Router>
          <Navbar />
          <AudioPlayer>
            <div className="main">
              <Routes>
                <Route path="/" exact element={<ContentPage />} />
                <Route path="/about" element={<AboutPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
              </Routes>
            </div>
          </AudioPlayer>
        </Router>
      </div>
    </div>
  );
}

export default App;
