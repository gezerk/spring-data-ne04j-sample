# spring-data-ne04j-sample
Sample project for demonstrating Spring Data for Neo4j issue with unique indexes on relationships.

# Domain Description
Catalogs contain listings of products.  Products are not unique in the domain, but they are exclusive to a catalog.
That is, a product can only be listed in a single catalog.

# Expectations
Given a Product with a relationship to a Catalog via a Listing (annotated with @Indexed(unique = true)
When I save the Product in the ProductRepository
Then the Product is saved without issue

# Actual
Using spring-data-neo4j:3.2.1.RELEASE or spring-data-neo4j:3.3.0.BUILD-SNAPSHOT the following exception:

```org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is java.lang.IllegalArgumentException: [com.gezerk.domain.Listing@7066363:com.gezerk.domain.Listing] is not a supported property value```

# Workarounds
No workarounds found, tried the following:

- Use converters - created custom converters for Listing and register with Conversion Service
-- Converters are never called.  I can use the same converter for a Node property and the converter is called
--- This is true even when specifying a @GraphProperty(propertyType = Listing.class) on the relationship