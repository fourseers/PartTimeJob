#!/usr/bin/python3

import argparse
import os
import sys

def execute(cmd):
    print(cmd)
    os.system(cmd)

execute("mvn clean package -Dmaven.test.skip=true")
execute("docker-compose build")
execute("docker-compose push")
execute("docker-compose config > /tmp/preprocessed-docker-compose.yml")
execute("env $(cat .env | xargs) docker stack deploy -c /tmp/preprocessed-docker-compose.yml backend")
