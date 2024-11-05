package org.rsb.tyr.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@Node
public class User {
  @Id @GeneratedValue private Long id;

  /** Name of the User */
  private String name;

  /** Password of the User */
  private String password;

  @Relationship(type = "ROLE")
  private AuthLevel authLevel;
}
