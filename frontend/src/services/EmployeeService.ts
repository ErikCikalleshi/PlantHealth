import axios from 'axios'

const API_BASE_URL = 'http://localhost:9000/api/test'

class EmployeeService {
    getEmployees() {
        return axios.get(API_BASE_URL);
    }
}

export default new EmployeeService();