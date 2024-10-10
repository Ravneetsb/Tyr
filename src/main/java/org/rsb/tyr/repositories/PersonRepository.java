package org.rsb.tyr.repositories;

import org.rsb.tyr.models.Allegation;
import org.rsb.tyr.models.Crime;
import org.rsb.tyr.models.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
  @Query("MATCH (p: Person {name:$0})-[:committed]->(c:Crime) RETURN c")
  Crime getCrime(String name);

  @Query("MATCH (p: Person {name:$0}) RETURN p")
  Person getByName(String name);

  @Query("match (p: Person {name:$0}) match (p)-[:committed]->(c:Crime) return p")
  Optional<Person> getByNameWithDetails(String name);

  @Query("MATCH (p:Person {name:$0})-[:allegation]->(a:Allegation)" + "RETURN collect(a)")
  Set<Allegation> getAllegations(String name);
}
