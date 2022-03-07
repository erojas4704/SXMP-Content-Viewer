import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import Api from "../../Api";

export const login = createAsyncThunk(
  "auth/login",
  async ({ email, password }) => {
    return await Api.login(email, password);
  }
);

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    isAuthenticated: false,
    user: null,
    error: null,
    token: null,
    status: "idle",
  },
  reducers: {},
  extraReducers(builder) {
    builder.addCase(login.pending, (state) => {
      state.status = "pending";
      state.user = null;
      state.error = null;
      state.token = null;
    })
    .addCase(login.fulfilled, (state, action) => {
      state.status = "fulfilled";
      //state.user = action.payload.user;
      state.token = action.payload; //TODO send back an object with payload and token
    })
    .addCase(login.rejected, (state, action) => {
      state.status = "error";
      state.error = action.payload;
    })
  },
});


export default authSlice.reducer;