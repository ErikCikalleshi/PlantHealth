#!/bin/bash

# Step 1: Check if Python is installed
python_command="python3"

cd accesspoint/

# Check if the virtual environment folder exists
if [ ! -d "venv" ]; then
    echo "Creating virtual environment folder..."
    python3 -m venv venv
fi

# Activate the virtual environment
echo "Activating the virtual environment..."
source venv/bin/activate

# Step 3: Install the required dependencies
echo "Installing requirements..."
pip install -r requirements.txt

echo "Installation completed. You are ready to start the Python project."