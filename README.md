# spring-data-ne04j-sample
Sample project for demonstrating Spring Data for Neo4j issue with unique indexes on relationships.

# Domain Description
Catalogs contain listings of products.  Products are not unique in the domain, but they are exclusive to a catalog.
That is, a product can only be listed in a single catalog.

Consider a stack of catlogs that may or may not have the same products in them.

# Expectations
Given an existing product with a relationship to a catalog via a listing (annotated with @Indexed(unique = true)

When I save a product with the same properties in the ProductRepository

Then the existing product is updated and only one product is in the repository

# Approach
Considering the domain and expectations it appears that having a unique relationship on Product.listing would give the desired behavior.
If a product exists in the graph and a new product with the same properties is saved the unique index on Listing causes SDN to merge instead of creating a second product.

# Actual
Using spring-data-neo4j:3.2.1.RELEASE or spring-data-neo4j:3.3.0.BUILD-SNAPSHOT the following exception is thrown when using a unique index:

```org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is java.lang.IllegalArgumentException: [com.gezerk.domain.Listing@7066363:com.gezerk.domain.Listing] is not a supported property value```

# Tests

There are two methods for setting a relationship according to
[Good Relationships](http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#reference_programming_model_relationships_relatedto).

Using a single field:
[Failing Test](src/test/java/com/gezerk/UniqueIndexTests.java) - shouldRetrieveSupplierFromProduct()

Using a relationship entity:
[Failing Test](src/test/java/com/gezerk/UniqueIndexTests.java) - shouldRetrieveCatalogFromProduct()

Both tests fail with the exception above.

# Observations

There is on open issue for non-primitive property types failing when using a unique index [here](https://jira.spring.io/browse/DATAGRAPH-479).  The exception listed in that issue is the same, a failure to recognize the property type.
It would appear that SDN is not calling the registered converter for the property type (converters are registered in the tests above).
The @GraphProperty(propertyType = Supplier.class) annotation was added to try and force loading of the converters.  The converters for Supplier and Catalog log when they are registered and when they are called.
In both tests, the converters were registered, but not called.

Debugging Spring Data Neo4j shows that the decision to load converters is made in the EntityStateHandler.class
(3.3.0-BUILD-SNAPSHOT line 170) by calling the Neo4jPersistentPropertyImpl.isSerializablePropertyField() method.  The isSerializablePropertyField() method returns false if the property is a relationship.
That would indicate that no conversion would ever occur for a relationship.


