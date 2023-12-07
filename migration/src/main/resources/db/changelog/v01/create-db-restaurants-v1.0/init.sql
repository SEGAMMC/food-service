CREATE SEQUENCE IF NOT EXISTS restaurant_seq;

CREATE TABLE IF NOT EXISTS restaurants
(
	id BIGINT NOT NULL DEFAULT NEXTVAL ('restaurant_seq'),
	name VARCHAR(100) NOT NULL,
	address TEXT NOT NULL,
	status VARCHAR(30),
	CONSTRAINT restaurants_id PRIMARY KEY (id)
    );

COMMENT ON TABLE restaurants IS 'Таблица ресторанов';
COMMENT ON COLUMN restaurants.id IS 'Идентификационный номер ресторана';
COMMENT ON COLUMN restaurants.name IS 'Название ресторана';
COMMENT ON COLUMN restaurants.address IS 'Адрес ресторана';
COMMENT ON COLUMN restaurants.status IS 'Статус ресторана';
