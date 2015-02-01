package com.gezerk;

import com.gezerk.domain.Catalog;
import com.gezerk.domain.Product;
import com.gezerk.repositories.CatalogRepository;
import com.gezerk.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by George Simpson on 1/31/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class UniqueIndexTests {

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


//        @Override
//        protected ConversionService neo4jConversionService() throws Exception {
//            ConverterRegistry converterRegistry = (ConverterRegistry) super.neo4jConversionService();
//            converterRegistry.addConverter(new StringToHostConverter());
//            converterRegistry.addConverter(new HostToStringConverter());
//            System.out.println("****************################### converters loaded");
//            return (ConversionService) converterRegistry;
//        }
    }

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void uniquePropertyCallsConverters(){

    }

    @Test
    public void uniqueRelationshipDoesNotCallConverters(){
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
}
