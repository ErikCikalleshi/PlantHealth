interface EventBus {
    on<T>(event: string, callback: (data: T) => void): void;
    dispatch<T>(event: string, data: T): void;
    remove<T>(event: string, callback: (data: T) => void): void;
}

const eventBus: EventBus = {
    on<T>(event: string, callback: (data: T) => void) {
        document.addEventListener(event, (e) => {
            callback((e as CustomEvent<T>).detail);
        });
    },
    dispatch<T>(event: string, data: T) {
        document.dispatchEvent(new CustomEvent<T>(event, { detail: data }));
    },
    remove<T>(event: string, callback: (data: T) => void) {
        document.removeEventListener(event, (e) => {
            callback((e as CustomEvent<T>).detail);
        });
    },
};

export default eventBus;