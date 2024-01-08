CREATE SEQUENCE IF NOT EXISTS order_item_seq;

CREATE TABLE IF NOT EXISTS order_items
(
	id BIGINT NOT NULL DEFAULT NEXTVAL('order_item_seq'),
	order_id UUID NOT NULL,
	restaurant_menu_item_id BIGINT NOT NULL,
	price NUMERIC NOT NULL,
	quantity INT NOT NULL,
	CONSTRAINT order_items_id PRIMARY KEY (id)
    );

COMMENT ON TABLE order_items IS 'Таблица с позициями из заказов (ресторан/цена за позицию/количество блюд)';
COMMENT ON COLUMN order_items.id IS 'Идентификационный номер позиции заказа';
COMMENT ON COLUMN order_items.order_id IS 'Идентификационный номер заказа';
COMMENT ON COLUMN order_items.restaurant_menu_item_id IS 'Идентификационный номер блюда в меню ресторана';
COMMENT ON COLUMN order_items.price IS 'Цена позиции в заказе (цена блюда*количество блюд)';
COMMENT ON COLUMN order_items.quantity IS 'Количество заказанных блюд данной позиции';