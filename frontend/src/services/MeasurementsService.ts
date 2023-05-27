import api from "@/services/api";

class MeasurementsService {
    async getMeasurementsByGreenhouseId(greenhouseId: number) {
        return await api.get('charts/get-measurements-by-greenhouse-id/' + greenhouseId, {});
    }
    async getMeasurementsByGreenhouseIdAndSensorType(greenhouseId: string, sensorType: string) {
        return await api.get('charts/get-measurements-by-greenhouse-id-and-sensor-type/' + greenhouseId + '/' + sensorType, {});
    }


}

export default new MeasurementsService();