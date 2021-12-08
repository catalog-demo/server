import axios from "axios";

const BASE_URL = "http://localhost:8080/api/";

class CatalogService{
    getCatalogs(syntax){
        return axios.get(BASE_URL + 'search?syntax=' + syntax);
    }
}

export default new CatalogService()