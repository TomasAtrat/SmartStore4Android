package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isEmailValid;
import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isStringNullOrEmpty;

import android.util.Patterns;

import com.zebra.rfid.demo.sdksample.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public CustomerService() {

    }

    public List<String> isCustomerValid(Customer customer) {
        List<String> errors = new ArrayList<>();

        if (isStringNullOrEmpty(customer.getId())) errors.add("El documento es un campo requerido");

        if (isStringNullOrEmpty(customer.getEmail()) && isStringNullOrEmpty(customer.getPhoneNumber()))
            errors.add("Se requiere especificar al menos un medio de contacto con el cliente");

        if (!isStringNullOrEmpty(customer.getEmail()) && !isEmailValid(customer.getEmail()))
            errors.add("El email proporcionado no es válido");

        return errors;
    }

}
