package com.gezerk.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gezerk.domain.Catalog;
import com.gezerk.domain.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by George Simpson on 2/1/2015.
 */
public class SupplierToStringConverter implements Converter<Supplier, String>{
    static final Logger log = LoggerFactory.getLogger(SupplierToStringConverter.class);

    @Override
    public String convert(Supplier supplier) {
        log.info("Converting Supplier to string");
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(supplier);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
