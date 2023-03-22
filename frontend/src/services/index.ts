import axios from 'axios'
const API_BASE_URL: string = 'http://localhost:9000';

export async function tryLogin(username: string, password: string): Promise<boolean> {
    return axios.post(API_BASE_URL + '/login', {
        username: username,
        password: password
    }).then((response) => response.status === 200)
        .catch((error) => {
            console.log("Invalid credentials.");
            return false;
        });
}