ALTER TABLE restaurant_menu_items
	ADD CONSTRAINT fk_restaurant_menu_item_restaurant
	FOREIGN KEY (restaurant_id) REFERENCES restaurants (id);