create database mercX;
CREATE TABLE users (
                       id UUID PRIMARY KEY, -- ID из Keycloak
                       email VARCHAR(255) UNIQUE,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица дополнительных данных пользователя
CREATE TABLE user_profiles (
                               id UUID PRIMARY KEY REFERENCES users(id), -- Связь с Keycloak ID
                               avatar_url TEXT,
                               subscription_type VARCHAR(50),
                               preferences JSONB
);