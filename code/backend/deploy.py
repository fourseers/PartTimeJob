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
execute("export .env")
execute("docker stack deploy -c docker-compose.yml backend")