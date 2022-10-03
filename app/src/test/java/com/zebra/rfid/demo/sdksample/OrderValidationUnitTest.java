package com.zebra.rfid.demo.sdksample;

import static org.junit.Assert.assertEquals;

import com.zebra.rfid.demo.sdksample.models.Branch;
import com.zebra.rfid.demo.sdksample.models.Customer;
import com.zebra.rfid.demo.sdksample.models.ExpeditionType;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.utils.enums.ExpeditionTypeEnum;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class OrderValidationUnitTest {

    private Customer customer;
    private OrderService orderService;

    @Before
    public void setUp(){
        customer = new Customer("12345678",
                "Tomás",
                "Rodríguez",
                "tomasatrat@gmail.com",
                "12345678");

        orderService = new OrderService();
    }

    @Test
    public void whenExpeditionTypeIsBOPISAndAcceptsPartialExpeditionIsTrueShouldReturnError(){
        OrderInfo orderInfo = new OrderInfo(new Date(),
                "",
                true,
                "",
                "",
                "",
                "",
                new ExpeditionType(ExpeditionTypeEnum.BOPIS.getValue()),
                new Branch(1L),
                customer);

        List<String> errors = this.orderService.isOrderValid(orderInfo);

        assertEquals(1, errors.size());
        assertEquals("No se puede aceptar expedición parcial de un pedido recogido en tienda", errors.get(0));
    }

    @Test
    public void whenExpeditionTypeIsSendToAddressAndAddressIsEmptyShouldReturnError(){
        ExpeditionType e = new ExpeditionType(ExpeditionTypeEnum.SEND_TO_ADDRESS.getValue());
        e.setDescription("Envía");

        OrderInfo orderInfo = new OrderInfo(new Date(),
                "",
                true,
                "",
                "",
                "",
                "",
                e,
                new Branch(1L),
                customer);

        List<String> errors = this.orderService.isOrderValid(orderInfo);

        assertEquals(1, errors.size());
        assertEquals("Debe especificarse una dirección si el tipo de expedición es de tipo Envía", errors.get(0));
    }

}
