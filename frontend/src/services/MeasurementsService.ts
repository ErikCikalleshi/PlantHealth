import api from "@/services/api";

class MeasurementsService {
    async getMeasurementsByGreenhouseId(greenhouseId: number) {
        return await api.get('charts/get-measurements-by-greenhouse-id/' + greenhouseId, {});
    }
    async getMeasurementsByGreenhouseIdAndSensorType(greenhouseId: number, sensorType: string) {
        return await api.get('charts/get-measurements-by-greenhouse-id-and-sensor-type/' + greenhouseId + '/' + sensorType, {});
    }


}

export default new MeasurementsService();