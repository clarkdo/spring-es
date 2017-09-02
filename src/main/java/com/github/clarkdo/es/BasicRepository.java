package com.github.clarkdo.es;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;

public interface BasicRepository extends ElasticsearchRepository<Basic, String> {

  List<Basic> findByAreaInAndOutletIn(Collection<String> area, Collection<String> outlet);

  @Query("{\"bool\":{\"filter\":[{\"bool\":{\"should\":[{\"term\":{\"area\":\"?0\"}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"area\"}}}}]}},{\"bool\":{\"should\":[{\"term\":{\"outlet\":\"?1\"}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"outlet\"}}}}]}}]}}")
  List<Basic> findBasic(String area, String outlet);

}
