CREATE TABLE books (
    id_Llibre SERIAL PRIMARY KEY UNIQUE,
    titol VARCHAR(255),
    autor VARCHAR(255),
    editorial VARCHAR(255),
    datapublicacio DATE,
    tematica VARCHAR(255)
);
