-- init_001.sql: Ma'lumotlar bazasini to'liq qayta yaratish va sozlash
-- 1) Jadval va tipni o'chirish
DROP TABLE IF EXISTS app_user CASCADE;
DROP TYPE IF EXISTS role_enum;

-- 2) Jadvalni yaratish
CREATE TABLE app_user (
                          id                   SERIAL PRIMARY KEY,
                          username             VARCHAR(255) UNIQUE NOT NULL,
                          password             VARCHAR(255) NOT NULL,
                          email                VARCHAR(255) UNIQUE NOT NULL,
                          full_name            VARCHAR(255),
                          profile_picture_url  VARCHAR(512),
                          role                 VARCHAR(50) NOT NULL DEFAULT 'USER',
                          blocked              BOOLEAN     NOT NULL DEFAULT FALSE
);

-- 3) faqat USER yoki ADMIN qabul qiladigan check
ALTER TABLE app_user
    ADD CONSTRAINT app_user_role_check
        CHECK (role IN ('USER','ADMIN'));

-- 3. Posts jadvali
DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
                       id          SERIAL PRIMARY KEY,
                       user_id     INTEGER    NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                       title       VARCHAR(255),
                       content     TEXT,
                       media_url   TEXT,
                       view_count  INT        DEFAULT 0,
                       share_count INT        DEFAULT 0,
                       created_at  TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
                       updated_at  TIMESTAMP  DEFAULT CURRENT_TIMESTAMP
);

-- 4. Comments jadvali
DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
                          id         SERIAL PRIMARY KEY,
                          content    TEXT,
                          user_id    INTEGER     REFERENCES app_user(id) ON DELETE CASCADE,
                          post_id    INTEGER     REFERENCES posts(id)   ON DELETE CASCADE,
                          created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

-- 5. Likes jadvali
DROP TABLE IF EXISTS likes;
CREATE TABLE likes (
                       id       BIGSERIAL PRIMARY KEY,
                       user_id  BIGINT    NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                       post_id  BIGINT    NOT NULL REFERENCES posts(id)    ON DELETE CASCADE,
                       liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT uq_like_user_post UNIQUE (user_id, post_id)
);

-- 6. Followers jadvali
DROP TABLE IF EXISTS followers;
CREATE TABLE followers (
                           id           SERIAL PRIMARY KEY,
                           follower_id  INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
                           followee_id  INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
                           followed_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT uq_follow UNIQUE (follower_id, followee_id)
);

-- 7. Messages jadvali
DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
                          id          SERIAL PRIMARY KEY,
                          content     TEXT,
                          sent_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          sender_id   INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
                          receiver_id INTEGER REFERENCES app_user(id) ON DELETE CASCADE
);

-- 8. Rewards jadvali
DROP TABLE IF EXISTS rewards;
CREATE TABLE rewards (
                         id         SERIAL PRIMARY KEY,
                         reason     VARCHAR(255),
                         user_id    INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
