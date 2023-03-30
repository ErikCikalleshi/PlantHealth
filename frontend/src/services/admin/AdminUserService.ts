import {API_BASE_URL} from "@/services";
import axios from "axios";

class AdminUserService{
    getAllUsers(){
        return axios.get(API_BASE_URL+'admin/get-all-users');
    }
    deleteUser(username: String){
        return axios.post(API_BASE_URL+'admin/delete-user/'+username);
    }
}
export default new AdminUserService();
