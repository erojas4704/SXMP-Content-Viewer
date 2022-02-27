import { combineReducers } from "redux";
import contentReducer from "./contentSlice";

export default combineReducers({
    content: contentReducer
})