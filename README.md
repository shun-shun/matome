# Dropwizard matome

## Running The Application

How to start the application.

1. Run `mvn package` to build your application
1. Setup the h2 database with `java -jar target/matome.jar db migrate config.yml`
1. Start application with `java -jar target/matome.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`

## Database Default SQL
```
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(1,'ニュース速報');
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(2,'VIP');
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(3,'アニメ・ゲーム');
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(4,'芸能');
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(5,'スポーツ');
INSERT INTO PUBLIC.M_CATEGORY(CATEGORYID,CATEGORYNAME) VALUES(6,'IT');

INSERT INTO PUBLIC.M_PROVIDING_DESTINATION (CATEGORYID, DIESTINATIONNAME, URL ) VALUES (6,'IT速報','http://blog.livedoor.jp/itsoku/index.rdf');
INSERT INTO PUBLIC.M_PROVIDING_DESTINATION (CATEGORYID, DIESTINATIONNAME, URL ) VALUES (3,'オレ的ゲーム速報@刃','http://jin115.com/index.rdf');

```