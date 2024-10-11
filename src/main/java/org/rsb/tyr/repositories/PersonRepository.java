package org.rsb.tyr.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.rsb.tyr.models.Allegation;
import org.rsb.tyr.models.Crime;
import org.rsb.tyr.models.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  @Query(
      "MATCH (p:Person)\n"
          + "OPTIONAL MATCH (p)-[:committed]->(c:Crime)\n"
          + "WITH p, COUNT(c) AS crimeCount\n"
          + "OPTIONAL MATCH (p)-[:allegation]->(a:Allegation)\n"
          + "WITH p, crimeCount, COUNT(a) AS allegationCount\n"
          + "OPTIONAL MATCH (p)-[:citizen_of]->(co:Country)<-[:citizen_of]-(other:Person)-[:committed]->(c:Crime)\n"
          + "WHERE other <> p\n"
          + "WITH p, crimeCount, allegationCount, COUNT(other) AS sameCountryCrimeCount\n"
          + "SET p.score = (crimeCount * 2) + (allegationCount * 0.6) + (sameCountryCrimeCount * 0.2)\n")
  void calculateScores();

  @Query("match (p: Person) return p order by p.score desc")
  List<Person> getByScores();

  @Query("match (p: Person {name:$0}) return p.score")
  Double getScore(String name);
}
