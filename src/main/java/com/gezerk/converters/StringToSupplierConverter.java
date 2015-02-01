package com.gezerk.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gezerk.domain.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

/**
 * Created by George Simpson on 2/1/2015.
 */
public class StringToSupplierConverter implements Converter<String, Supplier> {
    static final Logger log = LoggerFactory.getLogger(StringToSupplierConverter.class);


    @Override
    public Supplier convert(String supplier) {
        log.info("Converting string to Supplier");
        ObjectMapper om = new ObjectMapper();
        try {
            om.readValue(supplier, Supplier.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
