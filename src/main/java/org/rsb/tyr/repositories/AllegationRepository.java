package org.rsb.tyr.repositories;

import java.util.Set;
import org.rsb.tyr.models.Allegation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegationRepository extends Neo4jRepository<Allegation, Long> {
  @Query("MATCH (a: Allegation) match (p: Person {name:$0})-[:allegation]->(a) return a")
  Set<Allegation> findByName(String name);
}
