package org.rsb.tyr.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("AuthLevel")
public class AuthLevel {
  @Id @GeneratedValue private Long id;

  private String name;
}
