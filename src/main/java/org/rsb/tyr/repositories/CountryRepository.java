package org.rsb.tyr.repositories;

import org.rsb.tyr.models.Country;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends Neo4jRepository<Country, Long> {
  @Query("MATCH (c:Country) match (p: Person {name:$0})-[:citizen_of]->(c) return c ")
  Country findByName(String name);
}
