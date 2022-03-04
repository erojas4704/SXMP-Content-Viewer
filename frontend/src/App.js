import "./App.css";
import ContentPage from "./components/ContentPage";
import Playhead from "./components/Playhead";
import Navbar from "./components/Navbar";
import { useEffect, useState } from "react";
import Marquee from "./components/Marquee";
import AudioPlayer from "./components/AudioPlayer";

function App() {

  return (
    <div className="App">
      <div className="container">
        <Navbar />
        <AudioPlayer>
          <div className="main">
            <ContentPage />
          </div>
        </AudioPlayer>
      </div>
    </div>
  );
}

export default App;
