import type IUser from "@/interfaces/user/IUser";

export default interface IGreenhouse {
    uuid: number;
    id: number;
    name: string;
    description: string;
    location: string;
    gardener: IUser;
    lastContact: string;
    status: string;
    published: boolean;
}