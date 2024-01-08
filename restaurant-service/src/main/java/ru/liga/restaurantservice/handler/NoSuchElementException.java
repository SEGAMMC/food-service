package ru.liga.restaurantservice.handler;

public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(String message) {
		super(message);
    }
}
