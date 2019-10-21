package work.niter.wecoding.controller;

import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.service.CompService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author ZHAI
 */
@RestController
@RequestMapping("/comp")
public class CompController {

    @Autowired
    private CompService compService;
    @Autowired
    private TransportClient client;

    @PostMapping
    public ResponseEntity<Void> postStudentMsg(CompStudent compStudent) {
        compService.saveStudentMsg(compStudent);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageInfo<CompStudent>> getAllStudentMsg(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(compService.findAllStudentMsg(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<CompStudent>> findAllStudentInfo() {
        return ResponseEntity.ok(compService.findAllStuMsg());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name")
    public ResponseEntity<PageInfo<CompStudent>> getMsgByName(
            @RequestParam(name = "searchName", required = false) String name,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(compService.findMsgByName(name, page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Void> putMsgById(@RequestBody CompStudent compStudent) {
        compService.changeMsgById(compStudent);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMsg(@RequestBody CompStudent compStudent) {
        compService.removeMsg(compStudent);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id")
    public ResponseEntity<Map<String, Object>> getStuById(
            @RequestParam(name = "id") String id
    ) {
        if (id.isEmpty()) {
            throw new RestException(ExceptionEnum.ARGS_NOT_FOUND_ERROR);
        }
        GetResponse result =
                client.prepareGet("wecoding", "stu", id).get();
        if (!result.isExists()) {
            System.out.println(result);
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }

        return ResponseEntity.ok(result.getSource());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> getStuInfoByOthers(
            @RequestParam(name = "keywords", required = false) String keywords
    ) {
        MultiMatchQueryBuilder multiMatchQuery =
                QueryBuilders.multiMatchQuery(keywords,
                        "stu_id", "stu_gender",
                        "stu_name", "stu_dept");
        SearchRequestBuilder builder =
                client
                .prepareSearch("wecoding")
                .setTypes("stu")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(multiMatchQuery)
                .setFrom(0)
                .setSize(10);

        SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();

        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSourceAsMap());
        }
        return ResponseEntity.ok(result);
    }
}
