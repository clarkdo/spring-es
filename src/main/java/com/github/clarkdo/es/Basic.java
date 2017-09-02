package com.github.clarkdo.es;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "rule", type = "basic")
@Data
@Builder
public class Basic {

  @Id
  private String id;

  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String area;

  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String outlet;

  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String product;

  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String productType;

}
