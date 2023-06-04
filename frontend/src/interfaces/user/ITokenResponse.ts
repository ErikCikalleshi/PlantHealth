//Default response from the server when a user logs in
export default interface ITokenResponse {
    refreshToken: string
    token: string
    id: number;
    username:string
    firstname:string
    lastname:string
    createDate:string
    email: string
    roles:string[]
}
