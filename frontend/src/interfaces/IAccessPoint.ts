import type IGreenhouse from "@/interfaces/IGreenhouse";

export default interface IAccessPoint {
    id: number;
    name: string;
    description: string;
    location: string;
    transmissionInterval: number;
    greenhouses: IGreenhouse[];
    lastContact: string;
    status: string;
    published: boolean;
}