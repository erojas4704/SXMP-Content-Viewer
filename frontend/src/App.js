import "./App.css";
import ContentPage from "./components/ContentPage";
import Playhead from "./components/Playhead";
import Navbar from "./components/Navbar";
import PlayingContext from "./contexts/NowPlayingContext";
import { useEffect, useState } from "react";

function App() {
  //TODO condense all this into a custom hook.
  const [currentContent, setCurrentContent] = useState(null);
  const [audio, setAudio] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);

  useEffect(() => {
    if (audio) {
      isPlaying ? audio.play() : audio.pause();
    }
  }, [isPlaying, audio]);

  // useEffect(() => {
  //   if (audio) audio.pause();
  //   if (currentContent) {
  //     setAudio(currentContent.audio);
  //   }
  // }, [currentContent]);

  const play = (audioToPlay) => {
    pause();
    setIsPlaying(true);
    audioToPlay.play();
    setAudio(audioToPlay);
  }

  const pause = () => {
    console.log("Needs to pause");
    if(audio) audio.pause();
    setIsPlaying(false);
  }

  return (
    <div className="App">
      <PlayingContext.Provider
        value={{
          currentContent,
          setCurrentContent,
          isPlaying,
          setIsPlaying,
          audio,
          play,
          pause
        }}
      >
        <div className="container">
          <Navbar />
          <div className="main">
            <ContentPage />
            <Playhead
              className="playhead-main"
              content={currentContent}
              audio={audio}
            />
          </div>
        </div>
      </PlayingContext.Provider>
    </div>
  );
}

export default App;
