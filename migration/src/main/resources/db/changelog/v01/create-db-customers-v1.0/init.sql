CREATE SEQUENCE IF NOT EXISTS customers_seq;

CREATE TABLE IF NOT EXISTS customers
(
	id BIGINT NOT NULL DEFAULT NEXTVAL('customers_seq'),
	phone VARCHAR(15) NOT NULL UNIQUE,
	status VARCHAR(30),
	email VARCHAR(100) UNIQUE,
	address TEXT,
	CONSTRAINT customers_id PRIMARY KEY (id)
    );

COMMENT ON TABLE customers IS 'Таблица с информацией о клиентах';
COMMENT ON COLUMN customers.id IS 'Идентификационный номер клиента';
COMMENT ON COLUMN customers.phone IS 'Телефонный номер клиента';
COMMENT ON COLUMN customers.status IS 'Статус клиента';
COMMENT ON COLUMN customers.email IS 'Электронная почта клиента';
COMMENT ON COLUMN customers.address IS 'Адрес клиента';

