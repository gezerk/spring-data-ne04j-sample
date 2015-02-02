package com.gezerk.repositories;

import com.gezerk.domain.Supplier;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by George Simpson on 2/1/2015.
 */
public interface SupplierRepository extends GraphRepository<Supplier> {
}
