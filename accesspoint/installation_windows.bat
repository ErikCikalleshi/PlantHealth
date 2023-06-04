@echo off

REM Step 1: Check if Python is installed

set "python_command=python"

where %python_command% >nul 2>nul
if %errorlevel% neq 0 (
    echo Python not found. Please install Python 3.11 and try again.
    exit /b 1
)

REM Step 2: Create and activate the virtual environment (optional for Accesspoint/ folder)

REM Change to the Accesspoint/ folder
cd accesspoint/

REM Check if the virtual environment folder exists
if not exist "venv" (
    echo Creating virtual environment folder...
    python -m venv venv
)

REM Activate the virtual environment
echo Activating the virtual environment...
call venv\Scripts\activate.bat

REM Step 3: Install the required dependencies
echo Installing requirements...
pip install -r requirements.txt

echo Installation completed. You are ready to start the Python project.
