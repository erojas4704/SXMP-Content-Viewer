import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Api from "../../Api";
import { v4 as uuid } from "uuid";

export const searchForContent = createAsyncThunk(
  "content/searchForContent",
  async (query) => {
    return await Api.searchForContent(query);
  }
);

export const loadContent = createAsyncThunk(
  "content/getContent",
  async (id) => {
    return await Api.getContent(id);
  }
);

export const getAllContent = createAsyncThunk(
  "content/getAllContent",
  async () => {
    return await Api.getAllContent();
  }
);

export const createContent = createAsyncThunk(
  "content/createContent",
  async (content) => {
    return await Api.createContent(content);
  }
);

export const updateContent = createAsyncThunk(
  "content/updateContent",
  async ({ contentId, content }) => {
    return await Api.updateContent(contentId, content);
  }
);

export const reactToContent = createAsyncThunk(
  "content/reactToContent",
  async ({ contentId, rating, isFavorite }) => {
    return await Api.reactToContent(contentId, rating, isFavorite);
  }
);

export const deleteContent = createAsyncThunk(
  "content/deleteContent",
  async (contentId) => {
    return await Api.deleteContent(contentId);
  }
);

export const contentSlice = createSlice({
  name: "content",
  initialState: {
    content: {},
    search: [],
    status: "idle",
    searchField: "",
    sortBy: null,
    nowPlaying: null,
  },

  reducers: {
    setSearchField: (state, action) => {
      state.searchField = action.payload;
    },
    setSortBy: (state, action) => {
      state.sortBy = action.payload;
    },
  },

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
        const previousRating = content.rating;

        content.rating = rating || content.rating;
        if (rating > 0) {
          content.likes++;
          content.dislikes -= Math.abs(previousRating);
        } else if (rating < 0) {
          content.dislikes++;
          content.likes -= Math.abs(previousRating);
        }

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
      })
      .addCase(deleteContent.pending, (state, action) => {
        const contentId = action.meta.arg;
        state.content[contentId].status = "pending";
      })
      .addCase(deleteContent.rejected, (state, action) => {
        const contentId = action.meta.arg;
        state.content[contentId].status = "error";
        state.content[contentId].error = action.error;
      })
      .addCase(deleteContent.fulfilled, (state, action) => {
        const { contentId } = action.meta.arg;
        delete state.content[contentId];
      })
      .addCase(loadContent.pending, (state, action) => {
        const id = action.meta.arg;
        state.content[id].status = "pending";
      })
      .addCase(loadContent.rejected, (state, action) => {
        const id = action.meta.arg;
        state.content[id].status = "error";
        state.content[id].error = action.payload;
      })
      .addCase(loadContent.fulfilled, (state, action) => {
        const { id } = action.payload;
        state.content[id] = action.payload;
        state.content[id].status = "fulfilled";
      })
      .addCase(createContent.pending, (state, action) => {
        action.meta.id = uuid();
        const { id } = action.meta;
        state.content[id] = action.meta.arg;
        state.content[id].status = "pending";
      })
      .addCase(createContent.rejected, (state, action) => {
        const { id } = action.meta;
        state.content[id].status = "error";
        state.content[id].error = action.payload;
      })
      .addCase(createContent.fulfilled, (state, action) => {
        const oldId = action.meta.id;
        const { id } = action.payload;
        state.content[id] = action.payload;
        state.content[id].status = "fulfilled";
        delete state.content[oldId];
      })
      .addCase(updateContent.pending, (state, action) => {
        const { contentId } = action.meta.arg;
        state.content.oldData = { ...state.content[contentId] };
        state.content[contentId] = action.meta.arg.content;
        state.content[contentId].status = "pending";
      })
      .addCase(updateContent.rejected, (state, action) => {
        const { contentId } = action.meta.arg;
        state.content[contentId] = { ...state.content[contentId].oldData };
        state.content[contentId].status = "error";
        state.content[contentId].error = action.payload;
      })
      .addCase(updateContent.fulfilled, (state, action) => {
        const { contentId } = action.meta.arg;
        state.content[contentId] = action.payload;
        state.content[contentId].status = "fulfilled";
        delete state.content.oldData;
      });
  },
});

export const getContentArray = (state, doFilter = false) => {
  const contentArr = doFilter
    ? state.content.search.map((o) => state.content.content[o])
    : Object.values(state.content.content);

  return contentArr;
};

export const getContent = (state, id) => {
  return state.content.content[id];
};

export const { play, setSearchField, setSortBy } = contentSlice.actions;
export default contentSlice.reducer;
