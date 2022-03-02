import "./App.css";
import ContentPage from "./components/ContentPage";
import Playhead from "./components/Playhead";
import Navbar from "./components/Navbar";

const nowPlaying = {};

function App() {
  return (
    <div className="App">
      <div className="container">
        <Navbar />
        <div className="main">
          <ContentPage />
          <Playhead />
        </div>
      </div>
    </div>
  );
}

export default App;
