const axios = require("axios");

export const client = axios.create({
  baseURL: "/api",
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

  /**
   *
   * @param {string} email
   * @param {string} password
   */
  static async login(email, password) {
    try {
      const response = await client.post("/auth/login", { email, password });
      //TODO change reponse to be an object with token and user data.
      client.defaults.headers.authorization = `Bearer ${response.data.token}`;
      return response.data;
    } catch (err) {
      console.log("There was an error");
      console.log(err);
      client.defaults.headers.authorization = null;
      throw err;
    }
  }
}
