-- Crear tablas

create table UserApp
(
    user_id  VARCHAR PRIMARY KEY NOT NULL,
    password VARCHAR NOT NULL,
    name VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    role role_type DEFAULT 'Artista',
    fcoins INTEGER NOT NULL
);

create table Collection
(
    collection_id SERIAL PRIMARY KEY,
    user_id VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES UserApp (user_id)
);

create table Art
(

    image VARCHAR NOT NULL PRIMARY KEY,
    collection_id SERIAL,
    title VARCHAR NOT NULL,
    price INTEGER NOT NULL,
    FOREIGN KEY (collection_id)
        REFERENCES Collection (collection_id)
);

create table LikeArt
(
    like_id SERIAL PRIMARY KEY,
    user_id VARCHAR NOT NULL,
    image VARCHAR,
    FOREIGN KEY (user_id)
        REFERENCES UserApp (user_id),
    FOREIGN KEY (image)
        REFERENCES Art (image)
);

create table Ownership
(
    ownership_id SERIAL PRIMARY KEY,
    user_id VARCHAR NOT NULL,
    image VARCHAR,
    FOREIGN KEY (user_id)
        REFERENCES UserApp (user_id),
    FOREIGN KEY (image)
        REFERENCES Art (image)
);

CREATE TYPE wallet_type AS ENUM('Recarga', 'Venta', 'Compra');

CREATE TABLE wallet_history(
	wallet_id SERIAL PRIMARY KEY,
    user_id VARCHAR NOT NULL,
	wtype wallet_type,
    fcoins INTEGER NOT NULL,
	image VARCHAR,
    FOREIGN KEY (user_id)
        REFERENCES UserApp (user_id),
	FOREIGN KEY (image)
        REFERENCES Art (image)
);
