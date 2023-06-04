export default interface IAuditLog {
    id: number;

    date: Date;

    user: string;

    action: string;

    targetID: string;

    targetType: string;

    success: boolean;
}