package org.talend.example;

import org.apache.camel.examples.Customer;

public class CustomerTransformer {
    public void transform(Customer customer) {
        System.out.println(customer.getAddress().getAddressLine1());
    }
}
