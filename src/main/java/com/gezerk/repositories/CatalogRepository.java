package com.gezerk.repositories;

import com.gezerk.domain.Catalog;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by George Simpson on 1/31/2015.
 */
public interface CatalogRepository extends GraphRepository<Catalog> {
}
