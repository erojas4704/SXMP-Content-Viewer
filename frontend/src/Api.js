import axios from "axios";

export const client = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export default class Api {
  static async createContent(content) {
    try {
      const response = await client.post("/content", content);
      return response.data;
    } catch (err) {
      console.log(err);
      return err;
    }
  }

  static async updateContent(id, content) {
    const response = await client.put(`/content/${id}`, content);
    return response.data;
  }

  /**
   * Retrieves all content from the backend.
   * @returns {Promise<Content>} A promise that will return all the content that's stored in our backend.
   */
  static async getAllContent() {
    const response = await client.get("/content");
    return response.data;
  }

  /**
   * Retrieves content by ID from the backend.
   * @param {Number} id The id of the content to retrieve.
   * @returns {Promise<Content>} A promise that will return the content with the given id.
   */
  static async getContent(id) {
    const response = await client.get(`/content/${id}`);
    return response.data;
  }

  /**
   * Searches for content in the backend.
   * @param {String} term The search term.
   * @param {Promise<Array>} content An array of all the content we've found.
   */
  static async searchForContent(term) {
    const response = await client.get(`/content/search`, {
      params: {
        term,
      },
    });
    return response.data;
  }

  /**
   * Checks the bearer token to see if it's still valid.
   * If the token is valid, we keep it in the header.
   * If not, we remove it from the header.
   * @param {string} token Bearer token.
   * @returns
   */
  static async checkAuth(token) {
    client.defaults.headers.authorization = token ? `Bearer ${token}` : null;
    const response = await client.get("/auth");
    return response.data;
  }

  static async register(username, password, handle) {
    const response = await client.post("/auth/register", {
      username,
      password,
      handle,
    });
    return response.data;
  }

  static async login(username, password) {
    try {
      const response = await client.post("/auth/login", { username, password });
      client.defaults.headers.authorization = `Bearer ${response.data.token}`;
      return response.data;
    } catch (err) {
      console.log("There was an error");
      console.error(err);
      client.defaults.headers.authorization = null;
      throw err;
    }
  }

  /**
   * Calls upon the API to set an user's reaction to a particular content.
   * @param {Number} id The id of the content to react to.
   * @param {Number} rating A score between -1 and 1. 1 Being liked, -1 being disliked, 0 being ambivalent.
   * @param {Boolean} isFavorite Should the content be favorited? If this value is set, the rating will be ignored.
   */
  static async reactToContent(contentId, rating, isFavorite) {
    const route = this.getRatingRoute(rating, isFavorite);

    const response = await client.put(`/content/${contentId}/${route}`);
    return response.data;
  }

  static setStore(store) {
    store.subscribe(() => {
      const token = store.getState().auth.token;
      client.defaults.headers.authorization = token ? `Bearer ${token}` : null;
    });
  }

  /*Utils */
  static getRatingRoute(rating, isFavorite) {
    if (isFavorite != null) {
      return isFavorite ? "favorite" : "unfavorite";
    }

    if (rating === 0) {
      return "unlike";
    }

    return rating === 1 ? "like" : "dislike";
  }
}
