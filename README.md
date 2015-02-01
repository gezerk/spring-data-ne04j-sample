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

There are two methods for setting a relationship according to [Good Relationships](http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#reference_programming_model_relationships_relatedto).
The first takes a simple...
[Failing Test](src/test/java/UniqueIndexTests.java) - shouldRetrieveSupplierFromProduct()
The second
[Failing Test](src/test/java/UniqueIndexTests.java) - shouldRetrieveCatalogFromProduct()

# Observations

https://jira.spring.io/browse/DATAGRAPH-479


Research indicates that this error occurs when no converter is available for the Node property.  In this case the 'listing' property on Product.
Attempted to correct the issue by creating converters for Listing.class and registering them with ConversionService.
The converters are not called when the listing property on Product is a relationship.
Changing Product.listing to an entity property (by removing the @RelatedToVia annotation) causes the converters to be called and correctly converts the property.
This remains true even if I add @GraphProperty(propertyType = Listing.class) to the relationship.
