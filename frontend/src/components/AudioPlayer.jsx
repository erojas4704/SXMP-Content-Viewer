import { useEffect, useState } from "react";
import PlayingContext from "../contexts/NowPlayingContext";
import Marquee from "./Marquee";
import Playhead from "./Playhead";

const AudioPlayer = ({ children }) => {
  const [currentContent, setCurrentContent] = useState(null);
  const [audio, setAudio] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);

  const play = (audioToPlay) => {
    pause();
    setIsPlaying(true);
    audioToPlay.play();
    setAudio(audioToPlay);
  };

  const pause = () => {
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

  useEffect(() => {
    if (audio) {
      isPlaying ? audio.play() : audio.pause();
    }
  }, [isPlaying, audio]);

  return (
    <PlayingContext.Provider
      value={{
        play,
        pause,
        toggleAudio
      }}
    >
      <div className="audio-player">
        {children}
        <div className="bottom-bar">
          <Marquee show={audio !== undefined} text={currentContent?.title} />
          <Playhead
            content={currentContent}
            audio={audio}
            onToggle={() => toggleAudio(audio, currentContent)}
          />
        </div>
      </div>
    </PlayingContext.Provider>
  );
};
export default AudioPlayer;
