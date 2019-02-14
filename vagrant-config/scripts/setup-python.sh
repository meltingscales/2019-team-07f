#!/usr/bin/env bash

echo "Setting up Python."

# Install Python dependencies from Pipfile to system Python
cd /vagrant/tools/video-to-audio/
python3.6 -m pipenv install --deploy --system