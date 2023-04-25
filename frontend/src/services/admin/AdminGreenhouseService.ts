import api from "@/services/api";
import type IAccessPoint from "@/interfaces/IAccessPoint";
import type IGreenhouse from "@/interfaces/IGreenhouse";

class AdminGreenhouseService{
    async addGreenhouseToAccessPoint(greenhouse:IGreenhouse, accessPointId:number){
        return await api.post('admin/add-greenhouse-to-access-point/'+accessPointId, JSON.parse(JSON.stringify(greenhouse)));
    }
}
export default new AdminGreenhouseService();