
--SELECT setval('user_id_seq', COALESCE((SELECT MAX(id) FROM users) + 1, 1), false);
--
--SELECT setval('image_id_seq', COALESCE((SELECT MAX(id) FROM images) + 1, 1), false);

--SELECT setval('artist_id_seq', COALESCE((SELECT MAX(id) FROM artist) + 1, 1), false);

--TRUNCATE TABLE roles, users, user_roles, images, user_image, categories, genres, category_genres RESTART IDENTITY CASCADE;

INSERT INTO art_categories (category_name, category_image) VALUES ('Visual arts', 'image');
INSERT INTO art_categories (category_name, category_image) VALUES ('Musical arts', 'image');

INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Painting', 'Painting_image', 1);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Graphic', 'Graphic_image', 1);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Architecture', 'Architecture_image', 1);

INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Piano', 'Piano_image', 2);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Violin', 'Violin_image', 2);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Guitar', 'Guitar_image', 2);
--
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Portrait', 'Portrait_image', 1);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Landscape', 'Landscape_image', 1);

INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('The Engraving', 'The_Engraving_image', 2);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Print', 'Print_image',2);

INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Veduta', 'Veduta_image', 3);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Ruins', 'Ruins_image', 3);

INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Classical', 'Classical_image', 4);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Jazz', 'Jazz_image', 4);

INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Classical', 'Classical_image', 5);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Folk', 'Folk_image', 5);

INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Classical Guitar', 'Classical_Guitar_image', 6);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Rock', 'Rock_image', 6);
--
INSERT INTO article_categories (category_name) VALUES ('Representatives of the arts');
INSERT INTO article_categories (category_name) VALUES ('The history of the genre');
INSERT INTO article_categories (category_name) VALUES ('Technique of execution');
INSERT INTO article_categories (category_name) VALUES ('Bio about artist');
--
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Portrait Title', 'First Portrait Text', 1, 1);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Bio about Leo', 'Second Portrait Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Landscape Title', 'First Landscape Text', 2, 2);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Bio about Donato Title', 'Second Landscape Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First The Engraving Title', 'First The Engraving Text', 2, 3);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second The Engraving Title', 'Second The Engraving Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Print Title', 'First Print Text', 2, 4);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Print Title', 'Second Print Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Veduta Title', 'First Veduta Text', 2, 5);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Veduta Title', 'Second Veduta Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Ruins Title', 'First Ruins Text', 2, 6);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Ruins Title', 'Second Ruins Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Classical Title', 'First Classical Text', 2, 7);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Classical Title', 'Second Classical Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Jazz Title', 'First Jazz Text', 2, 8);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Jazz Title', 'Second Jazz Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Classical Title', 'First Classical Text', 2, 9);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Classical Title', 'Second Classical Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Folk Title', 'First Folk Text', 2, 10);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Folk Title', 'Second Folk Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Classical Guitar Title', 'First Classical Guitar Text', 2, 11);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Classical Guitar Title', 'Second Classical Guitar Text', 4, null);

INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Rock Title', 'First Rock Text', 2, 12);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Rock Title', 'Second Rock Text', 4, null);
--
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Leonardo di ser Piero da Vinci', 'LeonardoDaVinci.png', 2);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Donato di Niccolo di Betto Bardi', 'DonatoDiNiccolo.png', 4);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Michelangelo di Lodovico Buonarroti Simoni', 'Michelangelo.png', 6);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Raffaello Sanzio', 'Raffaello.png', 8);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Vincent Van Gogh', 'VincentVanGogh.png', 10);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Pablo Picasso', 'PabloPicasso.png', 12);

INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Johann Sebastian Bach', 'Bach.png', 14);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Wolfgang Amadeus Mozart', 'Mozart.png', 16);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Ludwig van Beethoven', 'Beethoven.png', 18);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Frederic Chopin', 'FredericChopin.png', 20);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('Pyotr Ilyich Tchaikovsky', 'Thaikovsky.png', 22);
INSERT INTO artists (artist_name, artist_image, article_id) VALUES ('George Gershwin', 'Gershwin.png', 24);
--
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_1_image', 1, 'photo_1')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_1_image', 1, 'photo_2')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_1_image', 1, 'photo_3')

INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_2_image', 6, 'photo_1')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_2_image', 6, 'photo_2')

INSERT INTO article_images (image_name, article_id, image_description) VALUES ('Article_2_image', 9, 'photo_1')
--
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (1, 1, 1);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (2, 2, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (3, 3, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (4, 4, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (5, 5, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (6, 6, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (7, 7, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (8, 8, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (9, 9, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (10, 10, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (11, 11, null);
INSERT INTO quizzes (art_genre_id, article_id, artist_id) VALUES (12, 12, null);
--
INSERT INTO questions (question, quiz_id, question_value) VALUES ('Kto polychil zarplaty?', 1, 20);
--
INSERT INTO answers (question_id, answer, is_correct) VALUES (1, 'Pupa', false);
INSERT INTO answers (question_id, answer, is_correct) VALUES (1, 'Lupa', false);
INSERT INTO answers (question_id, answer, is_correct) VALUES (1, 'Zalupa', true);
INSERT INTO answers (question_id, answer, is_correct) VALUES (1, 'Kyka', false);
--
INSERT INTO question_images(question_id, image_name) VALUES (1, '');
--
INSERT INTO roles (role_name) VALUES ('ROLE_USER');
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');
--
INSERT INTO users (username, email, password, user_bio, profile_image, art_category_id, points) VALUES ('appMobile', 'app@gmail.com', '$2a$10$LJhSqqnT8p/3h0Vs1zUktew3GKsg5DsOYlYZy01IDkYC1OYWnKHX.', 'Developer', 'e6ff0650-1e54-4ed7-96ed-d3ae585e5f9f.png', 1, 0);
--
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
--
INSERT INTO schools (school_name, art_category_id, description, school_image) VALUES ('Крутая школа №1', 1, 'Неебическое описание', '')
--
--


--INSERT INTO categories (category) VALUES ('Декоративно-Прикладное Искусство');
--INSERT INTO categories (category) VALUES ('Современное Искусство');
--INSERT INTO categories (category) VALUES ('Скульптура');
--INSERT INTO categories (category) VALUES ('Архитектура');

--INSERT INTO genres (genre_name) VALUES ('Исторический');
--INSERT INTO genres (genre_name) VALUES ('Батальный');
--INSERT INTO genres (genre_name) VALUES ('Анимализм');
--INSERT INTO genres (genre_name) VALUES ('Бытовой');
--INSERT INTO genres (genre_name) VALUES ('Натюрморт');

--INSERT INTO category_genres (category_id, genre_id) VALUES (1, 1);
--INSERT INTO category_genres (category_id, genre_id) VALUES (1, 2);
--INSERT INTO category_genres (category_id, genre_id) VALUES (1, 3);
----INSERT INTO category_genres (category_id, genre_id) VALUES (1, 4);
----INSERT INTO category_genres (category_id, genre_id) VALUES (1, 5);
----INSERT INTO category_genres (category_id, genre_id) VALUES (1, 6);
----INSERT INTO category_genres (category_id, genre_id) VALUES (1, 7);
----INSERT INTO category_genres (category_id, genre_id) VALUES (1, 8);
--INSERT INTO category_genres (category_id, genre_id) VALUES (2, 4);
--INSERT INTO category_genres (category_id, genre_id) VALUES (2, 5);
--INSERT INTO category_genres (category_id, genre_id) VALUES (2, 6);
