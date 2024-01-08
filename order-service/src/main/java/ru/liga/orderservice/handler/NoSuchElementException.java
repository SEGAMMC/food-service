package ru.liga.orderservice.handler;

public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(String message) {
		super(message);
    }
}
