docker run --rm -d --net=host --name redis redis
docker run --rm -d --net=host --name mongo mongo
docker run --rm -d -p 30553:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql mysql --lower_case_table_names=1
sleep 60
mysql -e "CREATE DATABASE parttimejob_user;" --host=192.168.2.55 --port=30553 -uroot -proot
docker run --rm -d -p 30551:8761 --name eureka parttimejob/eureka-server
sleep 20
docker run --rm -d -p 30552:8079 --name gateway --env EUREKA_SERVER=192.168.2.55 --env EUREKA_PORT=30551 parttimejob/gateway
docker run --rm -d --net=host --name auth --env EUREKA_SERVER=192.168.2.55 --env EUREKA_PORT=30551 parttimejob/auth --spring.datasource.url=jdbc:mysql://192.168.2.55:30553/parttimejob_user?useSSL=false --spring.redis.host=192.168.2.55 spring.data.mongodb.uri=mongodb://192.168.2.55:27017/parttimejob_auth --wechat.appid=wxa4a138d329178443 --wechat.appsecret=b6973fdd85fd632cb698c80ca757d6e0
