-- 1. Ma'lumotlar bazasini yaratish (ixtiyoriy, alohida bajariladi)
-- CREATE DATABASE social_media;

-- 2. ENUM turi: Role
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'role') THEN
CREATE TYPE role AS ENUM ('USER', 'ADMIN');
END IF;
END
$$;

-- 3. Users (app_user) jadvali
CREATE TABLE IF NOT EXISTS app_user (
                                        id SERIAL PRIMARY KEY,
                                        full_name VARCHAR(255),
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    blocked BOOLEAN DEFAULT FALSE,
    profile_picture_url TEXT,
    role role NOT NULL
    );

-- 4. Posts jadvali
CREATE TABLE IF NOT EXISTS posts (
                                     id SERIAL PRIMARY KEY,
                                     user_id INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
    title VARCHAR(255),
    content TEXT,
    media_url TEXT,
    view_count INT DEFAULT 0,
    share_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- 5. Comments jadvali
CREATE TABLE IF NOT EXISTS comments (
                                        id SERIAL PRIMARY KEY,
                                        content TEXT,
                                        user_id INTEGER REFERENCES app_user(id),
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- 6. Likes (postga yoqtirishlar)
CREATE TABLE IF NOT EXISTS likes (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_like_user_post UNIQUE (user_id, post_id)
    );

-- 7. Followers jadvali
CREATE TABLE IF NOT EXISTS followers (
                                         id SERIAL PRIMARY KEY,
                                         follower_id INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
    followee_id INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(follower_id, followee_id)
    );

-- 8. Messages jadvali
CREATE TABLE IF NOT EXISTS messages (
                                        id SERIAL PRIMARY KEY,
                                        content TEXT,
                                        sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        sender_id INTEGER REFERENCES app_user(id),
    receiver_id INTEGER REFERENCES app_user(id)
    );

-- 9. Rewards jadvali
CREATE TABLE IF NOT EXISTS rewards (
                                       id SERIAL PRIMARY KEY,
                                       reason VARCHAR(255),
    user_id INTEGER REFERENCES app_user(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
