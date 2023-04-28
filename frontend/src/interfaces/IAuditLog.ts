export default interface IAuditLog {
    id: number;
    usernameModifier: string;
    entityModified: string;
    action: string;
    timestamp: string;
}