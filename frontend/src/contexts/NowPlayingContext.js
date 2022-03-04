import React from "react";
const PlayingContext = React.createContext({
  content: null,
  audio: null,
  setContent: () => {},
  play: () => {},
  pause: () => {},
});
export default PlayingContext;
