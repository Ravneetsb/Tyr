package org.rsb.tyr.repositories;

import java.util.List;
import java.util.Optional;
import org.rsb.tyr.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
  @Query("MATCH (u:User {name:$name})-[r:ROLE]->(a:AuthLevel) RETURN u, a, r")
  Optional<User> findByName(String name);

  @Query(
      "match (a:AuthLevel {name:$authLevel}) CREATE (u:User {name:$user, password:$password})-[:ROLE]->(a)")
  void registerNewUser(String user, String password, String authLevel);

  @Query("MATCH (u:User)-[r:ROLE]->(a:AuthLevel) RETURN u, a, r")
  List<User> findAllUsers();

  @Query("MATCH (u:User {name:$userName}) SET u.denials = u.denials + 1")
  void denyEntry(String userName);

  @Query("match (u:User {name:$userName}) set u.allowed = u.allowed + 1")
  void allowEntry(String userName);
}
