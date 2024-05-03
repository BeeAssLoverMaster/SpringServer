
--SELECT setval('user_id_seq', COALESCE((SELECT MAX(id) FROM users) + 1, 1), false);
--
--SELECT setval('image_id_seq', COALESCE((SELECT MAX(id) FROM images) + 1, 1), false);

--SELECT setval('artist_id_seq', COALESCE((SELECT MAX(id) FROM artist) + 1, 1), false);

--TRUNCATE TABLE roles, users, user_roles, images, user_image, categories, genres, category_genres RESTART IDENTITY CASCADE;

INSERT INTO art_categories (category_name, category_image) VALUES ('Visual arts', 'image');
INSERT INTO art_categories (category_name, category_image) VALUES ('Musical arts', 'image');

INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Painting', 'image', 1);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Graphic', 'image', 1);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Art History', 'image', 1);
INSERT INTO art_types (type_name, type_image, art_category_id) VALUES ('Solfedgio', 'image', 2);
--
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Portrait', 'image', 1);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Landscape', 'image', 1);
INSERT INTO art_genres (genre_name, genre_image, art_type_id) VALUES ('Seascape', 'image', 1);
--
INSERT INTO artists (artist_name, artist_image) VALUES ('Leonardo di ser Piero da Vinci', 'image');
INSERT INTO artists (artist_name, artist_image) VALUES ('Donato di Niccolò di Betto Bardi', 'image');
INSERT INTO artists (artist_name, artist_image) VALUES ('Michelangelo di Lodovico Buonarroti Simoni', 'image');
INSERT INTO artists (artist_name, artist_image) VALUES ('Raffaello Sanzio', 'image');
--
INSERT INTO artist_genres (genre_id, artist_id) VALUES (1, 1);
INSERT INTO artist_genres (genre_id, artist_id) VALUES (1, 2);
INSERT INTO artist_genres (genre_id, artist_id) VALUES (1, 3);
INSERT INTO artist_genres (genre_id, artist_id) VALUES (1, 4);
--
INSERT INTO article_categories (category_name) VALUES ('Representatives of the arts');
INSERT INTO article_categories (category_name) VALUES ('The history of the genre');
INSERT INTO article_categories (category_name) VALUES ('Technique of execution');
INSERT INTO article_categories (category_name) VALUES ('Bio about artist');
--
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('First Article Title', 'First Article Text', 1, 1);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Second Article Title', 'Second Article Text', 4, 1);
INSERT INTO articles (title, text, article_category_id, genre_id) VALUES ('Third Article Title', 'Third Article Text', 2, 2);
--
INSERT INTO artist_articles (artist_id, article_id) VALUES (1, 1);
INSERT INTO artist_articles (artist_id, article_id) VALUES (2, 1);
INSERT INTO artist_articles (artist_id, article_id) VALUES (3, 1);
INSERT INTO artist_articles (artist_id, article_id) VALUES (4, 2);
--
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('image', 1, 'photo')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('image', 1, 'photo')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('image', 1, 'photo')
INSERT INTO article_images (image_name, article_id, image_description) VALUES ('image', 2, 'photo')
--
INSERT INTO quizzes (art_genre_id, article_id) VALUES (1, 1);
INSERT INTO quizzes (art_genre_id, article_id) VALUES (1, 2);
INSERT INTO quizzes (art_genre_id, article_id) VALUES (2, 3);
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
