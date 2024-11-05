package org.rsb.tyr.repositories;

import org.rsb.tyr.models.AuthLevel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.*;

import java.util.Optional;

public interface AuthLevelRepository extends Neo4jRepository<AuthLevel, Long> {
  @Query("match (a:AuthLevel {name:$name}) return a")
  Optional<AuthLevel> findByName(String name);
}
