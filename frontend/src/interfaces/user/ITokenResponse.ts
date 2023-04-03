export default interface ITokenResponse {
    id: number;
    email: string
    refreshToken: string
    token: string
    username:string
    roles:string[]
}