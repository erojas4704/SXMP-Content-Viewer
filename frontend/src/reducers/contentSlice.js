import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Api from "../Api";

export const getAllContent = createAsyncThunk(
  "content/getAllContent",
  async () => {
    return Api.getAllContent();
  }
);

export const contentSlice = createSlice({
  name: "content",
  initialState: {
    content: [],
    status: "idle",
  },
  extraReducers(builder) {
    builder
      .addCase(getAllContent.fulfilled, (state, action) => {
        state.content.content = action.payload;
        state.content.status = "fulfilled";
      })
      .addCase(getAllContent.pending, (state, action) => {
        state.content.status = "pending";
      })
      .addCase(getAllContent.rejected, (state, action) => {
        state.content.status = "error";
        state.content.error = action.payload;
      });
  },
});

export default contentSlice.reducer;