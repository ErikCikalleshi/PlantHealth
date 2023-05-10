import api from "@/services/api";

class MeasurementsService {
    async getMeasurementsByGreenhouseId(greenhouseId: number) {
        return await api.get('api/measurements/get-measurements-by-greenhouse-id/' + greenhouseId, {});
    }


}

export default new MeasurementsService();