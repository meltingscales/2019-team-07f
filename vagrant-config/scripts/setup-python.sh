#!/usr/bin/env bash

echo "Setting up Python."

# Install Python dependencies from Pipfile to system Python
cd /vagrant/tools/video-to-audio/
python3.6 -m pipenv install --deploy --system

# Install jep as it isn't included in Pipfile.
python3.6 -m pip install jep

# Set up paths for jep.
JEP_PATH=/usr/local/lib/python3.6/dist-packages/jep/

# Include jep's DLL/SO in the system PATH if it isn't already in it.
if [[ -z "${LD_LIBRARY_PATH}" ]]; then
  echo "LD_LIBRARY_PATH Env var is unset."
  echo "Adding jep's DLL/SO folder to /etc/environment"
    
  echo 'LD_LIBRARY_PATH='\""$JEP_PATH"\"'' >> /etc/environment
  tail /etc/environment -n 1
else
  echo "Path contains jep's DLL/SO folder."
fi
