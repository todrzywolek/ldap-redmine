#!/usr/bin/env bash

npm install
npm run-script build
docker build -t todrzywolek/redmine-frontend .
