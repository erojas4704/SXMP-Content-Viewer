const axios = require("axios");
const client = axios.create({
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
}
