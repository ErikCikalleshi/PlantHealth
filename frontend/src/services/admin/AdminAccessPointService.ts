import api from "@/services/api";
import type IAccessPoint from "@/interfaces/IAccessPoint";

class AdminAccessPointService {
    async getAllAccessPoints() {
        return await api.get('admin/get-all-access-points', {});
    }

    async deleteAccessPoint(id: number) {
        return await api.delete('admin/delete-access-point/' + id, {});
    }

    async getSingleAccessPoint(id: number) {
        return await api.get('admin/get-access-point/' + id, {});
    }

    async updateAccessPoint(accessPoint: IAccessPoint) {
        return await api.patch('admin/update-access-point/', JSON.parse(JSON.stringify(accessPoint)));
    }

    async deleteGreenhouseByIdAndAccessPoint(greenhouseId: number, id: number) {
        return await api.delete('admin/delete-greenhouse-by-id-and-access-point-uuid/' + greenhouseId + '/' + id, {});
    }

    async createNewAccessPoint(newAccessPoint: IAccessPoint) {
        return await api.post('admin/create-new-access-point/', JSON.parse(JSON.stringify(newAccessPoint)));
    }

    async getAccesPointByGreenhouseUuid(greenhouseUuid: number) {
        return await api.get('admin/get-access-point-by-greenhouse/' + greenhouseUuid, {});
    }
}

export default new AdminAccessPointService();