const axios = require("axios");

export default class Api {
  static client = axios.create({
    baseURL: "/api",
  });

  static async getAllContent() {
    try {
      const response = await this.client.get("/content");
      return response.data;
    } catch (err) {
      console.log(err);
      return err;
    }
  }

  static async getContent(id) {
    try {
      const response = await this.client.get("/content", { params: { id } });
      return response.data;
    } catch (err) {
      console.error(err);
      return err;
    }
  }
}
