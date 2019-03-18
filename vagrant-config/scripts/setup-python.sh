#!/usr/bin/env bash

echo "Setting up Python."

# Install Python dependencies from Pipfile to system Python
cd /home/vagrant/2019-team-07f/tools/video-to-audio/
python3.6 -m pipenv install --deploy --system

echo "Done setting up Python."