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

    async getGreenhouse(greenhouseUUID: number) {
        return await api.get('greenhouse/get/' + greenhouseUUID);
    }

    async updateGreenhouse(greenhouse: IGreenhouse) {
        return await api.put('gardener/greenhouse/update', JSON.parse(JSON.stringify(greenhouse)));
    }

    async getAllGreenhousesForCurrentUser(){
        return await api.get('gardener/greenhouse/get-all');
    }
}

export default new AdminGreenhouseService();