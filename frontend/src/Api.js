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

  static async getAllContent() {
    const response = await client.get("/content");
    return response.data;
  }

  static async getContent(id) {
    const response = await client.get(`/content/${id}`);
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
      //TODO change reponse to be an object with token and user data.
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
   * @param {Number} reaction A score between -1 and 1. 1 Being liked, -1 being disliked, 0 being ambivalent.
   */
  static async reactToContent(reaction) {
    const likeRoute =
      reaction === 0 ? "unlike" : reaction === 1 ? "like" : "dislike";

    const response = await client.put(`/content/:id/${likeRoute}`);
    return response.data;
  }

  static setStore(store) {
    store.subscribe(() => {
      const token = store.getState().auth.token;
      client.defaults.headers.authorization = token ? `Bearer ${token}` : null;
    });
  }
}
