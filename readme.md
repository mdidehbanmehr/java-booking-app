To get the database up and running, run the commands below
```sh
docker compose build
docker compose up
```
The test data is added inside the migration file. Potential improvement is to create a seeder class or gradle task.

running the application, it will run on port `9090`