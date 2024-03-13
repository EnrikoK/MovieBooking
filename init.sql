CREATE TABLE genres (
                        id bigserial NOT NULL,
                        genre varchar(255) NULL,
                        CONSTRAINT genres_pkey PRIMARY KEY (id)
);


-- public.movie definition

-- Drop table

-- DROP TABLE movie;

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

-- DROP TABLE users;

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

-- DROP TABLE movie_genre;

CREATE TABLE movie_genre (
                             genre_id int8 NOT NULL,
                             movie_id int8 NOT NULL,
                             CONSTRAINT fk3pdaf1ai9eafeypc7qe401l07 FOREIGN KEY (genre_id) REFERENCES genres(id),
                             CONSTRAINT fkp6vjabv2e2435at1hnuxg64yv FOREIGN KEY (movie_id) REFERENCES movie(id)
);


-- public.screening definition

-- Drop table

-- DROP TABLE screening;

CREATE TABLE screening (
                           "date" timestamp(6) NULL,
                           id bigserial NOT NULL,
                           movie_id int8 NULL,
                           CONSTRAINT screening_pkey PRIMARY KEY (id),
                           CONSTRAINT fkfp7sh76xc9m508stllspchnp9 FOREIGN KEY (movie_id) REFERENCES movie(id)
);


-- public.ticket definition

-- Drop table

-- DROP TABLE ticket;

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
                               ('Action'),
                               ('Comedy'),
                               ('Drama'),
                               ('Horror'),
                               ('Science Fiction');

-- Insert movies
INSERT INTO movie (description, "language", rating, title) VALUES
                                                               ('A thrilling action-packed movie', 'English', 'PG13', 'The Avengers'),
                                                               ('A hilarious comedy about friends', 'English', 'PG', 'Superbad'),
                                                               ('An emotional journey of love and loss', 'English', 'PG13', 'The Notebook'),
                                                               ('A terrifying horror film', 'English', 'R', 'The Conjuring'),
                                                               ('An epic sci-fi adventure', 'English', 'PG13', 'Interstellar');

-- Insert users
INSERT INTO users ("role", "password", username) VALUES
                                                     (1, 'password123', 'john_doe'),
                                                     (0, 'securepass', 'jane_smith'),
                                                     (1, 'abc123', 'alice_green');

-- Insert movie_genre relations
INSERT INTO movie_genre (genre_id, movie_id) VALUES
                                                 (1, 1),
                                                 (1, 5),
                                                 (2, 2),
                                                 (3, 3),
                                                 (4, 4),
                                                 (5, 5);

-- Insert screenings
INSERT INTO screening ("date", movie_id) VALUES
                                             ('2024-03-14 18:00:00', 1),
                                             ('2024-03-15 20:00:00', 2),
                                             ('2024-03-16 17:30:00', 3),
                                             ('2024-03-17 21:00:00', 4),
                                             ('2024-03-18 19:45:00', 5);

-- Insert tickets
INSERT INTO ticket ("row", seat, purchase_date, screening_id, user_id) VALUES
                                                                           (1, 5, '2024-03-14 15:00:00', 1, 1),
                                                                           (2, 10, '2024-03-15 12:30:00', 2, 2),
                                                                           (3, 15, '2024-03-16 09:45:00', 3, 3),
                                                                           (4, 20, '2024-03-17 16:20:00', 4, 1),
                                                                           (5, 25, '2024-03-18 14:15:00', 5, 2);
