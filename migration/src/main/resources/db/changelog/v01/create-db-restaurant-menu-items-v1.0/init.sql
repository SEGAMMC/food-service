CREATE SEQUENCE IF NOT EXISTS restaurant_menu_item_seq;

CREATE TABLE IF NOT EXISTS restaurant_menu_items
(
	id BIGINT NOT NULL DEFAULT NEXTVAL ('restaurant_menu_item_seq'),
	restaurant_id BIGINT NOT NULL,
	name TEXT NOT NULL,
	price NUMERIC NOT NULL,
	image_url TEXT,
	description TEXT,
	status VARCHAR(30),
	CONSTRAINT restaurant_menu_items_id PRIMARY KEY (id)
    );

COMMENT ON TABLE restaurant_menu_items IS 'Таблица с блюдами из ресторанов';
COMMENT ON COLUMN restaurant_menu_items.id IS 'Идентификационный номер блюда';
COMMENT ON COLUMN restaurant_menu_items.restaurant_id IS 'Идентификационный номер ресторана';
COMMENT ON COLUMN restaurant_menu_items.name IS 'Название блюда';
COMMENT ON COLUMN restaurant_menu_items.price IS 'Цена блюда';
COMMENT ON COLUMN restaurant_menu_items.image_url IS 'Ссылка на фотографию блюда';
COMMENT ON COLUMN restaurant_menu_items.description IS 'Описание блюда';
COMMENT ON COLUMN restaurant_menu_items.status IS 'Статус блюда';