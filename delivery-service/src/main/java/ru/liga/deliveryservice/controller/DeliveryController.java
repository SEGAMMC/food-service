package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.OrderStatusRequest;
import ru.liga.deliveryservice.dto.response.CourierResponse;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;
import ru.liga.deliveryservice.service.CourierService;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.UUID;

/**
 * Контроллер для работы с доставкой
 */
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
	
    private final DeliveryService deliveryService;
    private final CourierService courierService;

//    ХХХХХХХХ
	//
	// /orders?status=active/complete/denied
	//
	//{
    //"orders": [
    //    {
    //        "id": "",
    //        "menu_items": [
    //            {
    //               "quantity": "",
    //                "menu_item_id": ""
    //            }
    //        ]            
    //    }
    //],
    //"page_index": 0,
    //"page_count": 10
	//}
	
	
//	ЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧЧ
	//POST /delivery/${id}/
	//{
	//	"order_action": "accept/complete"
	//}
	
	

	
	
	/**
     * Получение списка доступных заказов для доставки с соответствующим
     * статусом заказа
	 *
     * @param status - статус заказа
     * @return возвращает список заказов на доставку с соответствиющим статусом
     */
    @GetMapping
    public ResponseEntity<DeliveriesResponse> getOrdersByStatus(@RequestParam (name = "courier") long courierId,
			@RequestParam (name = "status") String status) {
		return ResponseEntity.ok(deliveryService.getOrdersDeliveryByStatus(courierId, status));
    }
//			ХХХХХХХХХХХХХХХ
	//GET /deliveries?status=active/complete

	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	/**
     * Обновление статуса заказа
     *
     * @param uuid - идентификационный номер заказа
	 * @param orderStatus - новый статус заказа
     */
    @PutMapping("/order/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus){
        deliveryService.updateOrderStatus(uuid, orderStatus);
    }
}
