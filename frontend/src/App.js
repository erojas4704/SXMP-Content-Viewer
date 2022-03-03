import "./App.css";
import ContentPage from "./components/ContentPage";
import Playhead from "./components/Playhead";
import Navbar from "./components/Navbar";
import React from "react";

export const PlayingContext = React.createContext({});

function App() {
  return (
    <div className="App">
      <PlayingContext.Provider value={{}}>
        <div className="container">
          <Navbar />
          <div className="main">
            <ContentPage />
            <Playhead className="playhead-main" />
          </div>
        </div>
      </PlayingContext.Provider>
    </div>
  );
}

export default App;
