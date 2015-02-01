package com.gezerk;

import com.gezerk.converters.SupplierToStringConverter;
import com.gezerk.converters.ListingToStringConverter;
import com.gezerk.converters.StringToSupplierConverter;
import com.gezerk.converters.StringToListingConverter;
import com.gezerk.domain.Catalog;
import com.gezerk.domain.Product;
import com.gezerk.domain.Supplier;
import com.gezerk.repositories.CatalogRepository;
import com.gezerk.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by George Simpson on 1/31/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class UniqueIndexTests {
    static final Logger log = LoggerFactory.getLogger(UniqueIndexTests.class);

    @Configuration
    @EnableNeo4jRepositories
    static class TestConfig extends Neo4jConfiguration {

        TestConfig() throws ClassNotFoundException {
            setBasePackage("com.gezerk");
        }


        @Bean
        GraphDatabaseService graphDatabaseService() {
            return new TestGraphDatabaseFactory().newImpermanentDatabase();
        }


        @Override
        protected ConversionService neo4jConversionService() throws Exception {
            ConverterRegistry converterRegistry = (ConverterRegistry) super.neo4jConversionService();
            converterRegistry.addConverter(new StringToListingConverter());
            converterRegistry.addConverter(new ListingToStringConverter());
            converterRegistry.addConverter(new StringToSupplierConverter());
            converterRegistry.addConverter(new SupplierToStringConverter());
            log.info("Converters registered");
            return (ConversionService) converterRegistry;
        }
    }

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void shouldRetrieveCatalogFromProduct(){
        //Catalog is related to Product via Listing
        Catalog catalog = new Catalog("Cool Stuff");
        catalogRepository.save(catalog);
        assertThat("repository contains one catalog", catalogRepository.count(), is(1l));

        Product product = new Product();
        product.name = "widget";
        product.listedBy(catalog, "Widgets");

        productRepository.save(product);
        assertThat("product can be retrieved from repo", productRepository.findOne(product.nodeId), is(product));
        assertThat("category can be retrieved from product via relationship", productRepository.findOne(product.nodeId).listing.category, is("Widgets"));
        assertThat("catalog can be retrieved from product via relationship", catalogRepository.findOne(product.listing.catalog.nodeId), is(catalog));

    }

    @Test
    public void shouldRetrieveSupplierFromProduct(){
        //Supplier is related to product

        Product product = new Product();
        product.name = "widget";
        product.supplier = new Supplier("Woot Company");

        productRepository.save(product);
        assertThat("product can be retrieved from repo", productRepository.findOne(product.nodeId), is(product));
        assertThat("product contains supplier", productRepository.findOne(product.nodeId).supplier.name, is("Woot Company"));
    }


}
