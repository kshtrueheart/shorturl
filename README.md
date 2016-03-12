Short url service
=================

## Install Service
### Install Redis

```
$ wget http://download.redis.io/releases/redis-3.0.7.tar.gz
$ tar xzf redis-3.0.7.tar.gz
$ cd redis-3.0.7
$ make
$ src/redis-server
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