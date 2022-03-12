import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import ContentPage from "./components/ContentPage";
import AudioPlayer from "./components/AudioPlayer";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import AboutPage from "./pages/AboutPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import ContentForm from "./pages/ContentForm";
import ContentProfile from "./pages/ContentProfile";
import Api from "./Api";
import { store } from "./redux/store";
import SXMPNavbar from "./components/SXMPNavbar";

function App() {
  Api.setStore(store);

  return (
    <div className="App">
      <div>
        <Router>
          <SXMPNavbar />
          <AudioPlayer>
            <div className="main">
              <Routes>
                <Route path="/" exact element={<ContentPage />} />
                <Route
                  path="/playlist"
                  element={<ContentPage onlyShowFavorites={true} />}
                />
                <Route path="/about" element={<AboutPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/content/create" element={<ContentForm />} />
                <Route
                  path="/content/:id/update"
                  element={<ContentForm edit />}
                />
                <Route path="/content/:id" element={<ContentProfile />} />
              </Routes>
            </div>
          </AudioPlayer>
        </Router>
      </div>
    </div>
  );
}

export default App;
