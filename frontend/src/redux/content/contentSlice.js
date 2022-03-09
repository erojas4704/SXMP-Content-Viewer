import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Api from "../../Api";

export const getAllContent = createAsyncThunk(
  "content/getAllContent",
  async () => {
    return await Api.getAllContent();
  }
);

export const reactToContent = createAsyncThunk(
  "content/reactToContent",
  async ({ contentId, rating, isFavorite }) => {
    return await Api.reactToContent(contentId, rating, isFavorite);
  }
);

export const contentSlice = createSlice({
  name: "content",
  initialState: {
    content: {},
    status: "idle",
    nowPlaying: null,
  },

  reducers: {},

  extraReducers(builder) {
    builder
      .addCase(getAllContent.fulfilled, (state, action) => {
        state.content = action.payload.reduce((acc, curr) => {
          acc[curr.id] = curr;
          return acc;
        }, {});
        state.status = "fulfilled";
      })
      .addCase(getAllContent.pending, (state, action) => {
        state.status = "pending";
      })
      .addCase(getAllContent.rejected, (state, action) => {
        state.status = "error";
        state.error = action.payload;
      })
      .addCase(reactToContent.pending, (state, action) => {
        const { contentId, rating, isFavorite } = action.meta.arg;
        const content = state.content[contentId];
        content.rating = rating || content.rating;
        content.isFavorite =
          isFavorite !== null ? isFavorite : content.isFavorite;
        content.status = "pending";
      })
      .addCase(reactToContent.rejected, (state, action) => {
        const { contentId } = action.meta.arg;
        const content = state.content[contentId];
        content.status = "error";
      })
      .addCase(reactToContent.fulfilled, (state, action) => {
        const { contentId, rating, isFavorite } = action.meta.arg;
        const content = state.content[contentId];
        content.status = "fulfilled";
        content.rating = rating || content.rating;
        content.isFavorite =
          isFavorite !== null ? isFavorite : content.isFavorite;
      });
  },
});

export const getContentArray = (state) => Object.values(state.content.content);
export const { play } = contentSlice.actions;
export default contentSlice.reducer;
