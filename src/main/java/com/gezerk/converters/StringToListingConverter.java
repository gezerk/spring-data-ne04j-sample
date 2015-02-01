package com.gezerk.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gezerk.domain.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

/**
 * Created by George Simpson on 2/1/2015.
 */
public class StringToListingConverter implements Converter<String, Listing> {
    static final Logger log = LoggerFactory.getLogger(StringToListingConverter.class);


    @Override
    public Listing convert(String listing) {
        log.info("Converting string to Listing");
        ObjectMapper om = new ObjectMapper();
        try {
            om.readValue(listing, Listing.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
