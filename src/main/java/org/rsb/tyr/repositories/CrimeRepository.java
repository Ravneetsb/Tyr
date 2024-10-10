package org.rsb.tyr.repositories;

import org.rsb.tyr.models.Crime;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrimeRepository extends Neo4jRepository<Crime, Long> {
  @Query("MATCH (c:Crime) WHERE c.id = $name RETURN c")
  Optional<Crime> findByName(String name);

  @Query("MATCH (c:Crime) match (p: Person {name:$0})-[:committed]->(c) return c")
  Crime getCrime(String name);
}
