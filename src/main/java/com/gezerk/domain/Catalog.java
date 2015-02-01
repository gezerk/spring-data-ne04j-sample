package com.gezerk.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by George Simpson on 1/31/2015.
 */
@NodeEntity
public class Catalog {
    @GraphId
    public Long nodeId;

    public String name;


    public Catalog(){}

    public Catalog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "nodeId=" + nodeId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catalog)) return false;

        Catalog catalog = (Catalog) o;

        if (name != null ? !name.equals(catalog.name) : catalog.name != null) return false;
        if (nodeId != null ? !nodeId.equals(catalog.nodeId) : catalog.nodeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
