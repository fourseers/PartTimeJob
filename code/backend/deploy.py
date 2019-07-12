#!/usr/bin/python3

import argparse
import os
import sys

parser = argparse.ArgumentParser(description='Deploy the microservices')
parser.add_argument('--local-ip', help='local ip address')
parser.add_argument('--eureka-port', help='eureka-server port')
parser.add_argument('--gateway-port', help='gateway port')
parser.add_argument('--appid', help='wechat appid')
parser.add_argument('--secret', help='wechat app secret')

args = parser.parse_args()

if args.local_ip is None or args.eureka_port is None or args.gateway_port is None or args.appid is None or args.secret is None:
    print("Incorrect arguments")
    sys.exit(0)

os.system("cd auth && mvn clean package -Dmaven.test.skip=true dockerfile:build && cd ..")
os.system("cd eureka-server && mvn clean package -Dmaven.test.skip=true dockerfile:build && cd ..")
os.system("cd gateway && mvn clean package -Dmaven.test.skip=true dockerfile:build && cd ..")
os.system("cd warehouse && mvn clean package -Dmaven.test.skip=true dockerfile:build && cd ..")
os.system("cd arrangement && mvn clean package -Dmaven.test.skip=true dockerfile:build && cd ..")
os.system("docker rm -f eureka mongo redis mysql-inner auth gateway warehouse arrangement")
os.system("docker run --rm -d --net=host --name redis redis")
os.system("docker run --rm -d --net=host --name mongo mongo")
os.system("docker run --rm -d --net=host -e MYSQL_ROOT_PASSWORD=root --name mysql-inner mysql --lower_case_table_names=1")
os.system("docker run --rm -d -p {0}:8761 --name eureka parttimejob/eureka-server".format(args.eureka_port))
os.system("sleep 60")
os.system("mysql -e \"CREATE DATABASE parttimejob_user;\" --host={0} --port=3306 -uroot -proot".format(args.local_ip))
os.system("docker run --rm -d -p {0}:8079 --name gateway --env EUREKA_SERVER={1} --env EUREKA_PORT={2} parttimejob/gateway".format(args.gateway_port, args.local_ip, args.eureka_port))
os.system("docker run --rm -d --net=host --name auth --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/auth --spring.datasource.url=jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false --spring.datasource.password=root --spring.redis.host={3} --spring.data.mongodb.uri=mongodb://{4}:27017/parttimejob_auth --wechat.appid={5} --wechat.appsecret={6}".format(args.local_ip, args.eureka_port, args.local_ip, args.local_ip, args.local_ip, args.appid, args.secret))
os.system("docker run --rm -d --net=host --name warehouse --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/warehouse --spring.datasource.url=jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false --spring.datasource.password=root".format(args.local_ip, args.eureka_port, args.local_ip))
os.system("docker run --rm -d --net=host --name arrangement --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/arrangement --spring.datasource.url=jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false --spring.datasource.password=root".format(args.local_ip, args.eureka_port, args.local_ip))
