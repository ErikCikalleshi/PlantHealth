import json
import os

from auditlog_config import AuditLogger

logging = AuditLogger()


class Settings:
    _instance = None

    # cls for class method and self for instance method
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(Settings, cls).__new__(cls)
            cls._instance._initialize_settings()
        return cls._instance

    def _initialize_settings(self):
        setting = self.read_settings()
        self.auth_user = setting["auth"]["user"]
        self.auth_password = setting["auth"]["password"]
        self.auth = (self.auth_user, self.auth_password)
        self.server_host = setting["server"]["host"]
        self.server_port = setting["server"]["port"]
        self.access_point_id = setting["access_point_id"]
        self.mongo_port = setting["mongo"]["port"]
        self.mongo_host = setting["mongo"]["host"]
        self.mongo_database = setting["mongo"]["database"]
        self.mongo_collection = setting["mongo"]["collection"]

    def set_settings(self, custom_settings: dict):
        self.auth_user = custom_settings["auth"]["user"]
        self.auth_password = custom_settings["auth"]["password"]
        self.auth = (self.auth_user, self.auth_password)
        self.server_host = custom_settings["server"]["host"]
        self.server_port = custom_settings["server"]["port"]
        self.access_point_id = custom_settings["access_point_id"]
        self.mongo_port = custom_settings["mongo"]["port"]
        self.mongo_host = custom_settings["mongo"]["host"]
        self.mongo_database = custom_settings["mongo"]["database"]
        self.mongo_collection = custom_settings["mongo"]["collection"]

    @staticmethod
    def read_settings() -> dict:
        current_dir = os.path.dirname(os.path.abspath(__file__))
        settings_file_path = os.path.join(current_dir, "settings.json")

        if not os.path.exists(settings_file_path):
            logging.warning("Settings file not found. Creating...")
            os.makedirs(settings_file_path, exist_ok=True)
            settings_file_path = os.path.join(settings_file_path, "settings.json")
            with open(settings_file_path, "w") as f:
                json.dump({
                    "auth": {
                        "user": "admin",
                        "password": "passwd"
                    },
                    "server": {
                        "host": "10.0.0.153",
                        "port": 9000
                    },
                    "access_point_id": "1",
                    "mongo": {
                        "port": 27017,
                        "host": "localhost",
                        "database": "PlantHealth",
                        "collection": "Measurements"
                    }
                }, f)
                logging.info("Settings file created")
        with open(settings_file_path, "r") as data_file:
            data = json.load(data_file)
        logging.info("Settings file read")
        return data
