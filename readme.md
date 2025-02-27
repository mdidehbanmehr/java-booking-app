[//]: # (```sh)

[//]: # ()
[//]: # (docker run --name demo-db -e POSTGRES_PASSWORD='postgres' -d -p 5434:5432 postgres)

[//]: # ()
[//]: # (```)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (```sh)

[//]: # ()
[//]: # (docker exec -it demo-db bash)

[//]: # ()
[//]: # (```)

[//]: # ()
[//]: # ()
[//]: # (```)

[//]: # ()
[//]: # (psql -U postgres)

[//]: # (ALTER USER postgres WITH PASSWORD '<password of your choosing>';)

[//]: # (CREATE DATABASE demo;)

[//]: # ()
[//]: # (```)