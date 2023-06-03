export default interface ISensor {
    id: number|null;
    sensorType: string;
    limitLower: number|null;
    limitUpper: number|null;
    limitThresholdMinutes: number|null;
    displayLimitExceeded: boolean;
}