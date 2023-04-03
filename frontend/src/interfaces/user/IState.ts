import type ILoggedIn from "@/interfaces/user/ILoggedIn";
import type IUser from "@/interfaces/user/IUser";

export default interface IState{
    status:ILoggedIn
    user: IUser | null
    accessToken: string
}