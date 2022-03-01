import { combineReducers } from "redux";
import contentReducer from "./content/contentSlice";

export default combineReducers({
    content: contentReducer
})