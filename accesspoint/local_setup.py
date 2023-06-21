import json


def prompt_config_value(key):
    value = input(f"Enter the value for {key}: ")
    return value


def perform_quick_setup(server_host):
    return {
        "auth": {
            "user": "admin",
            "password": "passwd"
        },
        "server": {
            "host": server_host,
            "port": 9000
        },
        "access_point_id": "2",
        "mongo": {
            "port": 27017,
            "host": "localhost",
            "database": "PlantHealth",
            "collection": "Measurements"
        }
    }


def main():
    config = {}

    quick_setup = input("Perform quick setup? (yes/no): ").lower() == "yes"

    if quick_setup:
        server_host = prompt_config_value("server.host")
        config.update(perform_quick_setup(server_host))
    else:
        config["auth"] = {
            "user": prompt_config_value("auth.user"),
            "password": prompt_config_value("auth.password")
        }

        config["server"] = {
            "host": prompt_config_value("server.host"),
            "port": int(prompt_config_value("server.port"))
        }

        config["access_point_id"] = prompt_config_value("access_point_id")

        config["mongo"] = {
            "port": int(prompt_config_value("mongo.port")),
            "host": prompt_config_value("mongo.host"),
            "database": prompt_config_value("mongo.database"),
            "collection": prompt_config_value("mongo.collection")
        }

    with open('settings.json', 'w') as file:
        json.dump(config, file, indent=2)

    print("Configuration saved as settings.json")


if __name__ == "__main__":
    main()
