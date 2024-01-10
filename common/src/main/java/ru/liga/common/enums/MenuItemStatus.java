package ru.liga.common.enums;

public enum MenuItemStatus {
    ACTIVE,     // активно (блюдо видят все, отражается в меню)
    INACTIVE,    // не активно (блюдо видит ресторан, в меню не отражается)
    DELETE        // удалено (блюдо никто не видит)
}
