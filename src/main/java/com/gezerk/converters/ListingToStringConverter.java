package com.gezerk.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gezerk.domain.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by George Simpson on 2/1/2015.
 */
public class ListingToStringConverter  implements Converter<Listing, String>{
    static final Logger log = LoggerFactory.getLogger(ListingToStringConverter.class);

    @Override
    public String convert(Listing listing) {
        log.info("Converting Listing to string");
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(listing);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
