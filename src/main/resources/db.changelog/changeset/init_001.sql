-- Ma'lumotlar bazasini yaratish
CREATE DATABASE social_media;

-- Foydalanuvchi jadvali (users)
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255),
                       blocked BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Post jadvali
CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                       title VARCHAR(255),
                       content TEXT,
                       media_url TEXT,
                       view_count INT DEFAULT 0,
                       share_count INT DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comment jadvali
CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          content TEXT,
                          user_id INTEGER REFERENCES users(id),
                          post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Like jadvali (Postlarga yoqtirishlar)
CREATE TABLE post_likes (
                            post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
                            user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                            PRIMARY KEY (post_id, user_id)
);

-- Reward jadvali
CREATE TABLE rewards (
                         id SERIAL PRIMARY KEY,
                         reason VARCHAR(255),
                         user_id INTEGER REFERENCES users(id),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Message jadvali
CREATE TABLE messages (
                          id SERIAL PRIMARY KEY,
                          content TEXT,
                          sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          sender_id INTEGER REFERENCES users(id),
                          receiver_id INTEGER REFERENCES users(id)
);

-- Follower jadvali (Foydalanuvchilarni kuzatish)
CREATE TABLE followers (
                           id SERIAL PRIMARY KEY,
                           follower_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                           followee_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                           followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE(follower_id, followee_id)
);
-- likes jadvalini yaratish
CREATE TABLE IF NOT EXISTS likes (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     post_id BIGINT NOT NULL,
                                     liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                     CONSTRAINT fk_like_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_like_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT uq_like_user_post UNIQUE (user_id, post_id)
    );

