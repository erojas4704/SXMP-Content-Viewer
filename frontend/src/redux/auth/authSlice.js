import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import Api from "../../Api";

export const login = createAsyncThunk(
  "auth/login",
  async ({ email, password }) => {
    return await Api.login(email, password);
  }
);

export const register = createAsyncThunk(
  "auth/register",
  async ({ email, password, handle, confirmPassword }) => {
    if (password !== confirmPassword) throw new Error("Passwords do not match");
    return await Api.register(email, password, handle);
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
  reducers: {
    logout: (state) => {
      state.isAuthenticated = false;
      state.user = null;
      state.token = null;
      state.status =  "idle";
    }
  },
  extraReducers(builder) {
    builder
      .addCase(login.pending, (state) => {
        state.status = "pending";
        state.username = null;
        state.error = null;
        state.token = null;
      })
      .addCase(login.fulfilled, (state, action) => {
        state.status = "fulfilled";
        state.isAuthenticated = true;
        state.username = action.payload.username;
        state.token = action.payload.token;
        //TODO -> DRY
      })
      .addCase(login.rejected, (state, action) => {
        state.status = "error";
        state.error = action.error;
      })
      .addCase(register.pending, (state) => {
        state.status = "pending";
        state.isAuthenticated = false;
        state.username = null;
        state.error = null;
        state.token = null;
      })
      .addCase(register.fulfilled, (state, action) => {
        state.status = "fulfilled";
        state.isAuthenticated = true;
        state.username = action.payload.username;
        state.token = action.payload.token;
        //TODO -> DRY
      })
      .addCase(register.rejected, (state, action) => {
        state.status = "error";
        state.error = action.error;
      });
  },
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
