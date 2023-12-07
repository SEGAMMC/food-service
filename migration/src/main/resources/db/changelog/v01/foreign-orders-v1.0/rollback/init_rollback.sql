AlTER TABLE orders
	DROP FOREIGN KEY fk_orders_customers,
	DROP FOREIGN KEY fk_orders_couriers,
	DROP FOREIGN KEY fk_orders_restaurants;