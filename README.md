Short url service
=================

## Install Service
### Install PostgreSQL and create db

```
$ createdb shorturl
$ psql shorturl
$ // create urls tables
```

### Install ZooKeeper

### Package jar

```
$ mvn clean package
```

### Run service

```
$ java -jar ./target/shorturl-1.0.0-SNAPSHOT.jar
```

### Test

```
$ curl -i -X POST http://localhost:9010 -d 'url=https://github.com'
```