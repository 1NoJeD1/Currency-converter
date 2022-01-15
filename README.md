# Currency-cunverter

# PostgreSQL

Создаем базу
```
CREATE DATABASE converter_db;
```

Создаем пользователя
```
CREATE ROLE converter_user WITH LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT NOREPLICATION CONNECTION LIMIT -1 PASSWORD 'converter';
```

Даем права на базу converter_db
```
GRANT ALL PRIVILEGES ON DATABASE "converter_db" TO converter_user;
```

Даем все права на таблицы базы
```
GRANT ALL ON SCHEMA public TO converter_user;
```

Создаем структуру базы
```
START TRANSACTION;

    CREATE TABLE IF NOT EXISTS users (
      id serial,
      login VARCHAR(64),
      password VARCHAR(64),
      PRIMARY KEY(id)
    );

    INSERT INTO users (login, password) VALUES ('test', 'test');
	
    CREATE TABLE IF NOT EXISTS users_sessions (
      id VARCHAR(38) NOT NULL,
      user_id INT NOT NULL,
      expires DATE NOT NULL,
      PRIMARY KEY(id),
      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    );

    CREATE TABLE IF NOT EXISTS currency (
        id VARCHAR(10) NOT NULL,
        num_code VARCHAR(3) NOT NULL,
        char_code VARCHAR(3) NOT NULL,
        name VARCHAR(128) NOT NULL,
        PRIMARY KEY(id)
    );

    CREATE TABLE IF NOT EXISTS rate (
        id VARCHAR(10) NOT NULL,
        date DATE NOT NULL,
	      nominal INT NOT NULL,
        rate DECIMAL NOT NULL,
        PRIMARY KEY(id, date),
	      FOREIGN KEY (id) REFERENCES currency (id) ON DELETE CASCADE
    );

    CREATE TABLE IF NOT EXISTS history (
        id SERIAL,
        user_id INT NOT NULL,
        source VARCHAR(10) NOT NULL,
        target VARCHAR(10) NOT NULL,
        source_value DECIMAL NOT NULL,
        target_value DECIMAL NOT NULL,
        rate_date DATE NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
        FOREIGN KEY (source) REFERENCES currency (id),
        FOREIGN KEY (target) REFERENCES currency (id)
    );
	
    GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO converter_user;
COMMIT
```
