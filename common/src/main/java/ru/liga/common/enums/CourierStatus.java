package ru.liga.common.enums;

public enum CourierStatus {
    WORKS,      //работает, на смене
    NOT_WORK,   //не работает, выходной
    BUSY,       // занят, осуществляет доставку
    ACTIVE,     //профиль активный
    INACTIVE,    // профиль не активный
    BANNED      //  профиль заблокированный
}
