import type IUser from "@/interfaces/user/IUser";
import type ISensor from "@/interfaces/ISensor";

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
    sensors: ISensor[];
    accessPointName: string;
    displayLimitExceeded: boolean;
    displayIcon: boolean;
}