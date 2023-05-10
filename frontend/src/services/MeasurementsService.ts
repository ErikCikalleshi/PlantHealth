import api from "@/services/api";

class MeasurementsService {
    async getMeasurementsByGreenhouseId(greenhouseId: number) {
        return await api.get('measurements/get-measurement-by-greenhouseId/' + greenhouseId, {});
    }


}

export default new MeasurementsService();