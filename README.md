# Installation

```
It is tested in Mac OSX 10.14.6 with Docker CE 19.03.12 and docker-compose version 1.26.2.
It has not been tested in Linux and Windows.
```

---

## Prerequsite

```
1. Docker-ce must be installed
2. Docker demon must be run. 'Docker info' must work.
3. docker-compse must be installed. If not, please check https://docs.docker.com/compose/install/.
```

---

## Install

```
cd assignment/.
cp db/create_message_db.sql.original db/create_message_db.sql
bash create_sql_script.sh
docker-compose down
docker-compose rm -f
docker-compose build
docker-compose up -d redis db
docker-compose up -d fweb
docker-compose up -d web
```

---

## Test server

```
1. Open your browser in host machine and then try http://localhost:5000.
2. For the log, `docker logs springboot`.
3. For cheching the records of MySQL DB, `mysql -h 0.0.0.0 -P 3316 -u a_user -p`. For password, please check .evn file in the root directory. Or for convenience, please try http://localhost:8080/demo/all.
```

---

## Files:

```
web/: For Java spring boots.
fweb/: For Python Flask app.
db/: For msyql database.
redis_data/: For holding redis data.
mysql_data/: For holding mysql data.
```

---

## Note:

```
Change config info in .evn file as what you like.
```
