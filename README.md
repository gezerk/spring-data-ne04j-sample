# spring-data-ne04j-sample
Sample project for demonstrating Spring Data for Neo4j issue with unique indexes on relationships.

# Domain Description
Catalogs contain listings of products.  Products are not unique in the domain, but they are exclusive to a catalog.
That is, a product can only be listed in a single catalog.

# Expectations
Given a product with a relationship to a catalog via a listing (annotated with @Indexed(unique = true)

When I save the product in the ProductRepository

Then the product is saved and can be retrieved

# Actual
Using spring-data-neo4j:3.2.1.RELEASE or spring-data-neo4j:3.3.0.BUILD-SNAPSHOT the following exception is thrown:

```org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is java.lang.IllegalArgumentException: [com.gezerk.domain.Listing@7066363:com.gezerk.domain.Listing] is not a supported property value```

# Tests
[Failing Test](src/test/java/UniqueIndexTests.java) - shouldRetrieveCatalogFromProduct()

# Observations
Research indicates that this error occurs when no converter is available for the Node property.  In this case the 'listing' property on Product.
Attempted to correct the issue by creating converters for Listing.class and registering them with ConversionService.
The converters are not called when the listing property on Product is a relationship.
Changing Product.listing to an entity property (by removing the @RelatedToVia annotation) causes the converters to be called and correctly converts the property.
This remains true even if I add @GraphProperty(propertyType = Listing.class) to the relationship.
