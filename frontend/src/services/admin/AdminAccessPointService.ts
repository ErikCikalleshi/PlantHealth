import api from "@/services/api";

class AdminAccessPointService {
    async getAllAccessPoints() {
        return await api.get('admin/get-all-access-points', {
        });
    }

    async deleteAccessPoint(id: number) {
        return await api.delete('admin/delete-access-point/' + id, {
        });
    }
}
export default new AdminAccessPointService();