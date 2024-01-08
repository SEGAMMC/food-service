ALTER TABLE order_items
	ADD CONSTRAINT fk_order_items_order
	FOREIGN KEY (order_id) REFERENCES orders(uuid) ON delete CASCADE,
	
	ADD CONSTRAINT fk_order_items_restaurant_menu_item
	FOREIGN KEY (restaurant_menu_item_id) REFERENCES restaurant_menu_items (id) ON delete CASCADE;