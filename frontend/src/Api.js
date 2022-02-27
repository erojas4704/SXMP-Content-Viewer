const axios = require("axios");
class Api {
    static async getAllContent(){
        try{
            const response = await axios("/api/content")
        }
    }
}