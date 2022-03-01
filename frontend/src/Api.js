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

  static async getAllContent() {
    try {
      const response = await client.get("/content");
      return response.data;
    } catch (err) {
      console.log(err);
      return err;
    }
  }

  static async getContent(id) {
    try {
      const response = await client.get("/content", { params: { id } });
      return response.data;
    } catch (err) {
      console.error(err);
      return err;
    }
  }
}
