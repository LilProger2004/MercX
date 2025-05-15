create database mercX;
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        price VARCHAR(255) NOT NULL,
                        freelancer_id VARCHAR(255),
                        client_id VARCHAR(255) NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'new' CHECK (status IN ('new', 'in_progress', 'completed', 'cancelled')),
                        deadline TIMESTAMP,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        category VARCHAR(100),
                        attachments TEXT[], -- Массив ссылок на прикрепленные файлы
                        client_rating SMALLINT CHECK (client_rating BETWEEN 1 AND 5),
                        freelancer_rating SMALLINT CHECK (freelancer_rating BETWEEN 1 AND 5),
                        CONSTRAINT fk_freelancer FOREIGN KEY (freelancer_id) REFERENCES users(id) ON DELETE SET NULL,
                        CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Создаем индекс для часто используемых полей
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_client_id ON orders(client_id);
CREATE INDEX idx_orders_freelancer_id ON orders(freelancer_id);

-- Триггер для автоматического обновления updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_orders_updated_at
    BEFORE UPDATE ON orders
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();