package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isStringNullOrEmpty;

import android.content.Context;
import android.widget.Toast;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Customer;

public class CustomerService {
    private Context context;

    public CustomerService(Context context){
        this.context = context;
    }

    public boolean isCustomerValid(Customer customer){
        boolean isValid = true;

        if(isStringNullOrEmpty(customer.getId())){
            Toast.makeText(context, "El documento es un campo requerido", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if(isStringNullOrEmpty(customer.getEmail()) && isStringNullOrEmpty(customer.getPhoneNumber())){
            Toast.makeText(context, R.string.ContactoRequerido, Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

}
