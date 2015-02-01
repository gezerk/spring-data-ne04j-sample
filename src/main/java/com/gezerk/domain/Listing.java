package com.gezerk.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * Created by George Simpson on 2/1/2015.
 */
@RelationshipEntity
public class Listing {
    @GraphId Long id;
    @StartNode Product product;
    @EndNode public Catalog catalog;
    public String category;

    public Listing() {
    }

    public Listing(Product product, Catalog catalog, String category) {
        this.product = product;
        this.catalog = catalog;
        this.category = category;
    }
}
