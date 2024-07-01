# Clojure Notes App

## Requirements
- docker
- docker-compose
- node

### Start dockerized PostgreSQL

```shell
docker-compose up -d
```

### Prepare node-dependencies

```shell
lein shadow npm-deps
```

### Build frontend and run the backend

```shell
lein release && lein run
```

### Try Clojure Notes App

```shell
curl http://localhost:3000/
```

### Start development

Jack in a repl, execute

```clojure
(user/start-dev-system)
```

It will start up the shadow watch and the backend. It can be used to restart the whole application too.
