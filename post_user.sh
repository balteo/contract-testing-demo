#!/usr/bin/env bash

curl -d "@user.json" -H "Content-Type: application/json" -X POST "http://localhost:8080/api/sign-up"
