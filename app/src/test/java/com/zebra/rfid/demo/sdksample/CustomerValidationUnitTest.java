package com.zebra.rfid.demo.sdksample;

import static org.junit.Assert.assertEquals;

import com.zebra.rfid.demo.sdksample.models.Customer;
import com.zebra.rfid.demo.sdksample.services.CustomerService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomerValidationUnitTest {

    private CustomerService customerService;

    @Before
    public void setUp() {
        customerService = new CustomerService();
    }

    @Test
    public void emptyDocumentShouldReturnError() {
        Customer customer = new Customer("",
                "Tomás",
                "Rodríguez",
                "tomasatrat@gmail.com",
                "12345678");

        List<String> errors = customerService.isCustomerValid(customer);

        assertEquals(1, errors.size());

        assertEquals("El documento es un campo requerido", errors.get(0));
    }

    @Test
    public void emptyContactShouldReturnError(){
        Customer customer = new Customer("12345678",
                "",
                "",
                null,
                "");

        List<String> errors = customerService.isCustomerValid(customer);

        assertEquals(1, errors.size());

        assertEquals("Se requiere especificar al menos un medio de contacto con el cliente", errors.get(0));
    }

    @Test
    public void invalidEmailShouldReturnError(){
        Customer customer = new Customer("12345678",
                "Tomás",
                "Rodríguez",
                "tomasatrat",
                "12345678");

        List<String> errors = customerService.isCustomerValid(customer);

        assertEquals(1, errors.size());

        assertEquals("El email proporcionado no es válido", errors.get(0));
    }

    @Test
    public void customerShouldReturnNoErrors(){
        Customer customer = new Customer("12345678",
                "Tomás",
                "Rodríguez",
                "tomasatrat@gmail.com",
                "12345678");

        List<String> errors = customerService.isCustomerValid(customer);

        assertEquals(0, errors.size());
    }
}
