AlTER TABLE orders
	DROP FOREIGN KEY fk_order_items_order,
	DROP FOREIGN KEY fk_order_items_restaurant_menu_item;