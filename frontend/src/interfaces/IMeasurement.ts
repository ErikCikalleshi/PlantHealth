export default interface IMeasurement {
    accessPointUUID: number;
    date: string;
    greenhouseID: number;
    limitExceededBy: number;
    lowerLimit: number;
    upperLimit: number;
    measurementID: number;
    sensorType: string;
    value: number;
}