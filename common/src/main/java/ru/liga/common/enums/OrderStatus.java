package ru.liga.common.enums;

public enum OrderStatus {
    CUSTOMER_CREATED,    //заказ создан
    CUSTOMER_PAID,		 //заказ оплачен
    CUSTOMER_CANCELLED,  //заказ закрыт

    KITCHEN_ACCEPTED,    //заказ принят на кухню
    KITCHEN_PREPARING,   //заказ готовится
    KITCHEN_DENIED,      //в заказе отказано
    KITCHEN_REFUNDED,    //заказ возвращен

    DELIVERY_PENDING,    //ожидается доставка
    DELIVERY_PICKING,    //собирается заказ
    DELIVERY_DELIVERING, //осуществляется доставка
    DELIVERY_COMPLETE,    //доставка  выполнена
    DELIVERY_DENIED,     //в доставке отказано
    DELIVERY_REFUNDED    //заказ возвращен

//CUSTOMER_CREATED
//		|---------------------------|
//CUSTOMER_PAID				CUSTOMER_CANCELLED


//		|
//		|---------------------------|
//KITCHEN_ACCEPTED			KITCHEN_DENIED
//		|							|
//KITCHEN_PREPARING			KITCHEN_REFUNDED
//		|
//		|
//DELIVERY_PENDING
//		|---------------------------|
//DELIVERY_PICKING			DELIVERY_DENIED
//		|							|
//DELIVERY_DELIVERING		DELIVERY_REFUNDED
//		|
//DELIVERY_COMPLETE
}