import api from "@/services/api";
import type IAccessPoint from "@/interfaces/IAccessPoint";
import type IGreenhouse from "@/interfaces/IGreenhouse";

class AdminGreenhouseService {
    async addGreenhouseToAccessPoint(greenhouse: IGreenhouse, accessPointId: number) {
        return await api.post('admin/greenhouse/add', {
            greenhouse: JSON.parse(JSON.stringify(greenhouse)),
            sensors: JSON.parse(JSON.stringify(greenhouse.sensors)),
            accessPointId: accessPointId
        });
    };

}

export default new AdminGreenhouseService();