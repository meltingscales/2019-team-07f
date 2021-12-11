#!/usr/bin/env bash

pipenv install

pipenv run python test.py

cd jep-example
mvn package
mvn exec:java