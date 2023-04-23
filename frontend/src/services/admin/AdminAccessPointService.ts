import api from "@/services/api";
import type IAccessPoint from "@/interfaces/IAccessPoint";

class AdminAccessPointService {
    async getAllAccessPoints() {
        return await api.get('admin/get-all-access-points', {
        });
    }

    async deleteAccessPoint(id: number) {
        return await api.delete('admin/delete-access-point/' + id, {
        });
    }

    async getSingleAccessPoint(id: number) {
        return await api.get('admin/get-access-point/' + id, {
        });
    }

    updateAccessPoint(accessPoint: IAccessPoint) {
        return api.put('admin/update-access-point', accessPoint);
    }

    async deleteGreenhouseByIdAndAccessPoint(greenhouseId: number, id:number) {
        return await api.delete('admin/delete-greenhouse-by-id-and-access-point-uuid/' + greenhouseId + '/' + id, {
        });
    }
}
export default new AdminAccessPointService();