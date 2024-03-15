CREATE TABLE genres (
                        id bigserial NOT NULL,
                        genre varchar(255) NULL,
                        CONSTRAINT genres_pkey PRIMARY KEY (id)
);


-- public.movie definition

-- Drop table

--DROP TABLE movie;

CREATE TABLE movie (
                       id bigserial NOT NULL,
                       description varchar(255) NULL,
                       "language" varchar(255) NULL,
                       rating varchar(255) NULL,
                       title varchar(255) NULL,
                       CONSTRAINT movie_pkey PRIMARY KEY (id),
                       CONSTRAINT movie_rating_check CHECK (((rating)::text = ANY ((ARRAY['G'::character varying, 'PG'::character varying, 'PG13'::character varying, 'R'::character varying, 'NC17'::character varying])::text[])))
);


-- public.users definition

-- Drop table

--DROP TABLE users;

CREATE TABLE users (
                       "role" int2 NULL,
                       id bigserial NOT NULL,
                       "password" varchar(255) NOT NULL,
                       username varchar(255) NOT NULL,
                       CONSTRAINT users_pkey PRIMARY KEY (id),
                       CONSTRAINT users_role_check CHECK (((role >= 0) AND (role <= 1))),
                       CONSTRAINT users_username_key UNIQUE (username)
);


-- public.movie_genre definition

-- Drop table

--DROP TABLE movie_genre;

CREATE TABLE movie_genre (
                             genre_id int8 NOT NULL,
                             movie_id int8 NOT NULL,
                             CONSTRAINT fk3pdaf1ai9eafeypc7qe401l07 FOREIGN KEY (genre_id) REFERENCES genres(id),
                             CONSTRAINT fkp6vjabv2e2435at1hnuxg64yv FOREIGN KEY (movie_id) REFERENCES movie(id)
);


-- public.screening definition

-- Drop table

--DROP TABLE screening;

CREATE TABLE screening (
                           "date" timestamp(6) NULL,
                           id bigserial NOT NULL,
                           movie_id int8 NULL,
                           CONSTRAINT screening_pkey PRIMARY KEY (id),
                           CONSTRAINT fkfp7sh76xc9m508stllspchnp9 FOREIGN KEY (movie_id) REFERENCES movie(id)
);


-- public.ticket definition

-- Drop table

--DROP TABLE ticket;

CREATE TABLE ticket (
                        "row" int4 NOT NULL,
                        seat int4 NOT NULL,
                        id bigserial NOT NULL,
                        purchase_date timestamp(6) NULL,
                        screening_id int8 NULL,
                        user_id int8 NULL,
                        CONSTRAINT ticket_pkey PRIMARY KEY (id),
                        CONSTRAINT fkmvugyjf7b45u0juyue7k3pct0 FOREIGN KEY (user_id) REFERENCES users(id),
                        CONSTRAINT fkslsbfjfvsw5v43w11jm31x0c6 FOREIGN KEY (screening_id) REFERENCES screening(id)
);

-- Insert genres
INSERT INTO genres (genre) VALUES
                               ('Komöödia'),
                               ('Draama'),
                               ('Märul'),
                               ('Põnevik'),
                               ('Animatsioon'),
                               ('Romantika');

-- Insert movies
INSERT INTO movie (description, "language", rating, title) VALUES
                                                               ('Väike linnuke satub suurde seiklusesse. Ühel päeval avastab väike linnuke, et tema pesa on hävitatud ja ta peab leidma uue kodu. Algab põnev seiklus üle metsade ja mägede.', 'Eesti', 'PG', 'The Matrix'),
                                                               ('Kahe armastava hinge südantlõhestav lugu. Noored armastavad teineteist rohkem kui midagi muud maailmas, kuid saatusel on nende jaoks eriline plaan, mis paneb nende armastuse proovile.', 'Inglise', 'PG13', 'Titanic'),
                                                               ('Tulevikumaailmas peavad inimesed võitlema masinate vastu. Aastal 2050 on maailm muutunud tundmatuseni, kus inimesed peavad võitlema ülemaailmse masinate ülevõimu vastu. Üks kangelane astub välja, et lõpetada see hullumeelsus.', 'Inglise', 'R', 'Robovõitlus'),
                                                               ('Kangelane võitleb kurja ülemvõimu vastu. Vapralt Edasi on lugu noorest talupojast, kes peab võitlema pimeduse jõududega, et päästa oma koduküla ja armastatu.', 'Eesti', 'PG13', 'Vapralt Edasi'),
                                                               ('Salapärane mõrv, kus kõik on kahtlusalused. Ühes väikeses külas toimub salapärane mõrv, kus kõik külaelanikud on kahtlusalused. Üks detektiiv peab lahendama selle keerulise juhtumi enne, kui on liiga hilja.', 'Inglise', 'R', 'Mõrv Mustas Öös');



-- Insert movie_genre relations
INSERT INTO movie_genre (genre_id, movie_id) VALUES
                                                (1, 1),
                                                (6, 1),
                                                (2, 2),
                                                (3, 3),
                                                (4, 3),
                                                (4, 4),
                                                (1, 4),
                                                (5, 5);

-- Insert screenings
INSERT INTO screening ("date", movie_id) VALUES
                                             ('2024-06-15 18:00:00', 1),
                                             ('2024-06-16 20:00:00', 2),
                                             ('2024-06-17 19:30:00', 3),
                                             ('2024-06-18 21:00:00', 4),
                                             ('2024-06-19 17:45:00', 5);
