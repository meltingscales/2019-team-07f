#!/usr/bin/env bash


if hash netdata 2>/dev/null; then

    echo "Netdata is already installed."

else

    echo "Installing netdata as it is not installed."

    # Install netdata.
    bash <(curl -Ss https://my-netdata.io/kickstart.sh) --non-interactive

fi