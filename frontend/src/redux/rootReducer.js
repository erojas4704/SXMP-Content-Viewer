import { combineReducers } from "redux";
import contentReducer from "./content/contentSlice";
import authReducer from "./auth/authSlice";

export default combineReducers({
    content: contentReducer,
    auth: authReducer
});