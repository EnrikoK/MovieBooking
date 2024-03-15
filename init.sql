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
                               ('Draama'),
                               ('Komöödia'),
                               ('Ulme'),
                               ('Põnevik'),
                               ('Märul'),
                               ('Romantika'),
                               ('Seiklus'),
                               ('Animatsioon'),
                               ('Dokumentaal'),
                               ('Fantastika');

-- Insert movies
INSERT INTO movie (title, description, "language", rating) VALUES
                                                               ('Forrest Gump', 'Forrest Gump on südamlik lugu lihtsast mehest, kelle südame headus ja siirus puudutab kõiki, kes temaga kohtuvad.', 'Estonian', 'PG13'),
                                                               ('The Shawshank Redemption', 'Shawshanki lunastus on haarav draama kahest mehest, kelle sõprus ja lootus aitavad neil üle elada ka kõige raskemad ajad.', 'Estonian', 'R'),
                                                               ('Inception', 'Algus on keeruline ja mõistatuslik seiklus, kus reaalsus ja unenäod põimuvad omavahel ning kõik on võimalik.', 'Estonian', 'PG13'),
                                                               ('Pulp Fiction', 'Pulp Fiction on tempokas ja omapärane lugu mitmest erinevast tegelasest, kelle teed ristuvad ootamatul moel.', 'Estonian', 'R'),
                                                               ('The Matrix', 'Matrix on põnev ja visuaalselt muljetavaldav ulmefilm, mis viib vaataja virtuaalsetesse maailmadesse ja identiteedikriisidesse.', 'Estonian', 'R');


-- Insert movie_genre relations
-- Drop existing constraints on movie_genre table
ALTER TABLE movie_genre DROP CONSTRAINT fk3pdaf1ai9eafeypc7qe401l07;
ALTER TABLE movie_genre DROP CONSTRAINT fkp6vjabv2e2435at1hnuxg64yv;

-- Populate movie_genre table with associations between movies and genres
INSERT INTO movie_genre (genre_id, movie_id) VALUES
                                                 (1, 1),  -- Forrest Gump (Draama)
                                                 (2, 2),  -- The Shawshank Redemption (Komöödia)
                                                 (1, 2),  -- The Shawshank Redemption (Draama)
                                                 (4, 3),  -- Inception (Põnevik)
                                                 (10, 3), -- Inception (Fantastika)
                                                 (4, 4),  -- Pulp Fiction (Põnevik)
                                                 (5, 4),  -- Pulp Fiction (Märul)
                                                 (3, 5),  -- The Matrix (Ulme)
                                                 (5, 5);  -- The Matrix (Märul)


-- Insert screenings
INSERT INTO screening ("date", movie_id) VALUES
                                             ('2024-06-20 18:00:00', 1),
                                             ('2024-06-21 19:30:00', 2),
                                             ('2024-06-22 20:00:00', 3),
                                             ('2024-06-23 17:45:00', 4),
                                             ('2024-06-24 21:15:00', 5);
