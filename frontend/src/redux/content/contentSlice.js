import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Api from "../../Api";

export const searchForContent = createAsyncThunk(
  "content/searchForContent",
  async (query) => {
    return await Api.searchForContent(query);
  }
);

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
    search: [],
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
        if (rating > 0) content.likes++;
        else if (rating < 0) content.dislikes++;

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
        const { contentId } = action.meta.arg;
        state.content[contentId] = action.payload;
      })
      .addCase(searchForContent.pending, (state, action) => {
        state.status = "pending";
        state.search = [];
      })
      .addCase(searchForContent.rejected, (state, action) => {
        state.status = "error";
        state.search = [];
        state.error = action.payload;
      })
      .addCase(searchForContent.fulfilled, (state, action) => {
        //Merge search results with current content.
        state.content = action.payload.reduce(
          (acc, curr) => {
            acc[curr.id] = curr;
            return acc;
          },
          { ...state.content }
        );

        state.search = action.payload.map((o) => o.id);
        state.status = "fulfilled";
      });
  },
});

export const getContentArray = (state, doFilter = false) => {
  if (doFilter) {
    return state.content.search.map((o) => state.content.content[o]);
  }
  return Object.values(state.content.content);
};

export const { play } = contentSlice.actions;
export default contentSlice.reducer;
