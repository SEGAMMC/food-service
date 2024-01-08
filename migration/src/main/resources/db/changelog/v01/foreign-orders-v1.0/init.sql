ALTER TABLE orders
	ADD CONSTRAINT fk_orders_customers
	FOREIGN KEY (customer_id) REFERENCES customers (id) ON delete CASCADE,
	
	ADD CONSTRAINT fk_orders_couriers
	FOREIGN KEY (courier_id) REFERENCES couriers (id) ON delete CASCADE,
	
	ADD CONSTRAINT fk_orders_restaurants
	FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON delete CASCADE;
	