package com.gezerk.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

/**
 * Created by George Simpson on 1/31/2015.
 */
@NodeEntity
public class Product {
    @GraphId
    public Long nodeId;

    public String name;

//    @Fetch
//    public Catalog catlog;//Products are not unique, but they are exclusive.  A product can only be listed in one catalog.

    @Fetch
    @Indexed(unique = true)
    @RelatedToVia(type = "LISTED_IN", direction = Direction.OUTGOING)
    public Listing listing;

    public Listing listedBy(Catalog catalog, String category){
        Listing listing = new Listing(this, catalog, category);
        this.listing = listing;
        return listing;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "nodeId=" + nodeId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (nodeId != null ? !nodeId.equals(product.nodeId) : product.nodeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
