CREATE SEQUENCE IF NOT EXISTS couriers_seq;

CREATE TABLE IF NOT EXISTS couriers
(
	id BIGINT NOT NULL DEFAULT NEXTVAL('couriers_seq'),
	phone VARCHAR(15) NOT NULL UNIQUE,
	status VARCHAR(30),
	coordinates VARCHAR(50),
	CONSTRAINT couriers_id PRIMARY KEY (id)
    );

COMMENT ON TABLE couriers IS 'Таблица с информацией о курьерах';
COMMENT ON COLUMN couriers.id IS 'Идентификационный номер курьера';
COMMENT ON COLUMN couriers.phone IS 'Телефонный номер курьера';
COMMENT ON COLUMN couriers.status IS 'Статус курьера';
COMMENT ON COLUMN couriers.coordinates IS 'Текущие координаты курьера';