import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Api from "../../Api";

export const getAllContent = createAsyncThunk(
  "content/getAllContent",
  async () => {
    return await Api.getAllContent();
  }
);

export const contentSlice = createSlice({
  name: "content",
  initialState: {
    content: [],
    status: "idle",
    nowPlaying: null,
  },

  reducers: {},

  extraReducers(builder) {
    builder
      .addCase(getAllContent.fulfilled, (state, action) => {
        state.content = action.payload;
        state.status = "fulfilled";
      })
      .addCase(getAllContent.pending, (state, action) => {
        state.status = "pending";
      })
      .addCase(getAllContent.rejected, (state, action) => {
        state.status = "error";
        state.error = action.payload;
      });
  },
});

export const { play } = contentSlice.actions;
export default contentSlice.reducer;