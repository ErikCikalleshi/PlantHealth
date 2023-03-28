import {API_BASE_URL} from "@/services";
import axios from "axios";

class AdminUserService{
    getAllUsers(){
        console.log(API_BASE_URL+'admin/get-all-users');
        return axios.get(API_BASE_URL+'admin/get-all-users');
    }
}
export default new AdminUserService();
