package com.github.clarkdo.es;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.existsQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Controller
@EnableAutoConfiguration
public class App {

  @Resource
  BasicRepository basicRepo;

  @Resource
  ElasticsearchTemplate template;

  QueryBuilder termQueryNull(String term, String value) {
    return boolQuery().should(termQuery(term, value))
        .should(boolQuery().mustNot(existsQuery(term)));
  }

  @GetMapping("/config")
  @ResponseBody
  List<Basic> config() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery((
      boolQuery().filter(
        boolQuery()
          .must(termQueryNull("area", "上海"))
          .must(termQueryNull("outlet", "新天地店"))
          .must(termQueryNull("product", "战狼2"))
          .must(termQueryNull("productType", "3D"))))
    ).build();
    // when
    List<Basic> result1 = template.queryForList(searchQuery, Basic.class);
    List<Basic> result2 = basicRepo.findBasic("上海", "新天地店");
    List<Basic> result3 = basicRepo.findByAreaInAndOutletIn(
        Arrays.asList("上海"),
        Arrays.asList("新天地店")
    );
    return result1 == null ? result2 : result3;
  }

  @GetMapping("/create")
  @ResponseBody
  void create() {
    basicRepo.save(
        Basic.builder()
            .id("1").area("上海")
            .product("战狼2").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("2").area("上海").outlet("新天地店")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("3").area("上海").outlet("新天地店")
            .product("战狼2").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("4").area("上海").outlet("新天地店")
            .product("敦刻尔克").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("5").area("上海").outlet("新世界店")
            .product("战狼2").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("6").area("上海").outlet("万达广场")
            .product("敦刻尔克").productType("2D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("7").area("北京").outlet("新天地店")
            .product("战狼2").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("8").area("北京").outlet("万达广场")
            .product("战狼2").productType("3D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("9").area("北京").outlet("万达广场")
            .product("敦刻尔克").productType("2D")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("10").area("上海")
            .build()
    );
    basicRepo.save(
        Basic.builder()
            .id("11").product("战狼2")
            .build()
    );
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }
}
