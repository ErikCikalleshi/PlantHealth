import axios from 'axios'

const API_BASE_URL = 'http://localhost:9000/api/'

class EmployeeService {
    getEmployees() {
        return axios.get(API_BASE_URL + 'test');
    }

    postMethod() {
        return axios.post(API_BASE_URL + 'measurements', { plantID: 3, value: 23.0, unit: "celcius"});
    }
}

export default new EmployeeService();