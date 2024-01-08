CREATE TABLE IF NOT EXISTS orders
(
	uuid UUID NOT NULL,
	customer_id BIGINT NOT NULL,
	courier_id BIGINT,
	restaurant_id BIGINT NOT NULL,
	status VARCHAR (30),
	timestamp TIMESTAMPTZ,
	CONSTRAINT orders_id PRIMARY KEY (uuid)
);

COMMENT ON TABLE orders IS 'Таблица с заказами';
COMMENT ON COLUMN orders.uuid IS 'Идентификационный номер заказа';
COMMENT ON COLUMN orders.customer_id IS 'Идентификационный номер клиента';
COMMENT ON COLUMN orders.courier_id IS 'Идентификационный номер курьера';
COMMENT ON COLUMN orders.restaurant_id IS 'Идентификационный номер ресторана';
COMMENT ON COLUMN orders.status IS 'Статус заказа';
COMMENT ON COLUMN orders.timestamp IS 'Дата и время выполнения заказа';