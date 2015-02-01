# spring-data-ne04j-sample
Sample project for demonstrating Spring Data for Neo4j issue with unique indexes on relationships.

#H1 Domain Description
Catalogs contain listings of products.  Products are not unique in the domain, but they are exclusive to a catalog.
That is, a product can only be listed in a single catalog.

#H1 Expectations
Given a Product with a relationship to a Catalog via a Listing (annotated with @Indexed(unique = true)
When I save the Product in the ProductRepository
Then the Product is saved without issue

#H1 Actual - as of spring-data-neo4j:3.2.1.RELEASE and spring-data-neo4j:3.3.0.BUILD-SNAPSHOT
An error is thrown:

```org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is org.springframework.dao.InvalidDataAccessResourceUsageException: Error executing statement MERGE (n:`Product` {`listing`: {value}}) ON CREATE SET n={props}  return n; nested exception is java.lang.IllegalArgumentException: [com.gezerk.domain.Listing@7066363:com.gezerk.domain.Listing] is not a supported property value```