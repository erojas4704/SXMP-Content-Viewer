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

  //TODO move all this playhead stuff to an HOC

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
  };

  const pause = () => {
    console.log("Needs to pause");
    if (audio) audio.pause();
    setIsPlaying(false);
  };

  /**
   * Toggles an audio file. If it's already playing, we will pause it.
   * If it's not, we will replace the currently playing audio with this one.
   * @param {Audio} audio The audio to play.
   * @param {Object} contentData The content data that is being played. This will be used for our marquee. 
   */
  const toggleAudio = (audioToPlay, contentData) => {
    setCurrentContent(contentData);
    if (audio === audioToPlay && isPlaying) pause();
    else play(audioToPlay);
  };

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
          pause,
          toggleAudio,
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
              onToggle={() => toggleAudio(audio, currentContent)}
            />
          </div>
        </div>
      </PlayingContext.Provider>
    </div>
  );
}

export default App;
