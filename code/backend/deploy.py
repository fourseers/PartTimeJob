#!/usr/bin/python3

import argparse
import os
import sys

parser = argparse.ArgumentParser(description='Deploy the microservices')
parser.add_argument('--local-ip', required=True, help='local ip address')
parser.add_argument('--eureka-port', required=True, help='eureka-server port')
parser.add_argument('--gateway-port', required=True, help='gateway port')
parser.add_argument('--appid', required=True, help='wechat appid')
parser.add_argument('--secret', required=True, help='wechat app secret')

def execute(cmd):
    print(cmd)
    os.system(cmd)

args = parser.parse_args()

execute("docker rm -f eureka mongo redis mysql-inner auth gateway warehouse arrangement billing")
execute("docker run --rm -d --net=host --name redis redis")
execute("docker run --rm -d --net=host --name mongo mongo")
execute("docker run --rm -d --net=host -e MYSQL_ROOT_PASSWORD=root -e TZ='Asia/Shanghai' --name mysql-inner mysql --lower_case_table_names=1 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --skip-character-set-client-handshake")
execute("mvn clean package -Dmaven.test.skip=true")
execute("cd eureka-server && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("docker run --rm -d -p {0}:8761 --name eureka parttimejob/eureka-server".format(args.eureka_port))
execute("cd gateway && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("cd warehouse && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("cd auth && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("cd arrangement && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("cd billing && mvn -Dmaven.test.skip=true dockerfile:build && cd ..")
execute("mysql -e \"CREATE DATABASE parttimejob_user CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;\" --host={0} --port=3306 -uroot -proot".format(args.local_ip))
execute("docker run --rm -d --net=host --name arrangement --env EUREKA_SERVER={0} --env EUREKA_PORT={1} --env ES_REST_HOST={2} --env ES_COMM_HOST={2} parttimejob/arrangement --spring.datasource.url='jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false&useUnicode=true&characterEncoding=utf8' --spring.datasource.password=root".format(args.local_ip, args.eureka_port, args.local_ip))
execute("docker run --rm -d -p {0}:8079 --name gateway --env EUREKA_SERVER={1} --env EUREKA_PORT={2} parttimejob/gateway".format(args.gateway_port, args.local_ip, args.eureka_port))
execute("docker run --rm -d --net=host --name warehouse --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/warehouse --spring.datasource.url='jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false&useUnicode=true&characterEncoding=utf8' --spring.datasource.password=root".format(args.local_ip, args.eureka_port, args.local_ip))
execute("docker run --rm -d --net=host --name billing --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/billing --spring.datasource.url='jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false&useUnicode=true&characterEncoding=utf8' --spring.datasource.password=root".format(args.local_ip, args.eureka_port, args.local_ip))
execute("docker run --rm -d --net=host --name auth --env EUREKA_SERVER={0} --env EUREKA_PORT={1} parttimejob/auth --spring.datasource.url='jdbc:mysql://{2}:3306/parttimejob_user?useSSL=false&useUnicode=true&characterEncoding=utf8' --spring.datasource.password=root --spring.redis.host={3} --spring.data.mongodb.uri=mongodb://{4}:27017/parttimejob_auth --wechat.appid={5} --wechat.appsecret={6}".format(args.local_ip, args.eureka_port, args.local_ip, args.local_ip, args.local_ip, args.appid, args.secret))
