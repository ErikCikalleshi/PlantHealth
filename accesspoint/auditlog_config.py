import logging
import os.path
import inspect


class AuditLogger:
    _instance = None
    
    def __new__(cls, log_file='./audit.log'):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
            # Set up the logger
            cls._instance.logger = logging.getLogger('audit_logger')
            cls._instance.logger.setLevel(logging.DEBUG)

            # Create a file handler
            log_file_path = os.path.abspath(log_file)
            file_handler = logging.FileHandler(log_file_path)
            file_handler.setLevel(logging.DEBUG)

            # Create a console handler for warnings and errors
            console_handler = logging.StreamHandler()
            console_handler.setLevel(logging.WARNING)

            # Create a formatter
            formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

            # Add the formatter to the handlers
            file_handler.setFormatter(formatter)
            console_handler.setFormatter(formatter)

            # Add the handlers to the logger
            cls._instance.logger.addHandler(file_handler)
            cls._instance.logger.addHandler(console_handler)

        return cls._instance

    def info(self, message):
        print(message)
        frame = inspect.stack()[1]
        filename = os.path.relpath(frame.filename)
        lineno = frame.lineno
        self.logger.info(f"({filename}:{lineno}){message}")

    def warning(self, message):
        print(message)
        frame = inspect.stack()[1]
        filename = os.path.relpath(frame.filename)
        lineno = frame.lineno
        self.logger.warning(f"({filename}:{lineno}){message}")

    def error(self, message):
        print(message)
        frame = inspect.stack()[1]
        filename = os.path.relpath(frame.filename)
        lineno = frame.lineno
        self.logger.error(f"({filename}:{lineno}){message}")
