package com.gezerk.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by George Simpson on 2/1/2015.
 */
@NodeEntity
public class Supplier {
    @GraphId Long nodeId;
    public String name;

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }
}
