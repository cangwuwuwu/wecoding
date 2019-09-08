package work.niter.wecoding.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.service.CompService;

import java.util.List;

@RestController
@RequestMapping("/comp")
public class CompController {

    @Autowired
    private CompService compService;


    //存储学生信息
    @PostMapping
    public ResponseEntity<Void> postStudentMsg(CompStudent compStudent){
        compService.saveStudentMsg(compStudent);
        return ResponseEntity.ok().build();
    }

    //查询所有并分页
    @GetMapping
    public ResponseEntity<PageInfo<CompStudent>> getAllStudentMsg(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(compService.findAllStudentMsg(page, size));
    }

    //查询所有数据不分页
    @GetMapping("/all")
    public ResponseEntity<List<CompStudent>> findAllStudentInfo(){
        return ResponseEntity.ok(compService.findAllStuMsg());
    }

    //根据名字模糊查询
    @GetMapping("/name")
    public ResponseEntity<PageInfo<CompStudent>> getMsgByName(@RequestParam(name = "searchName", required = false) String name,
                                                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(compService.findMsgByName(name, page, size));
    }

    //修改学生信息
    @PutMapping
    public ResponseEntity<Void> putMsgById(@RequestBody CompStudent compStudent){
        compService.changeMsgById(compStudent);
        return ResponseEntity.ok().build();
    }

    //删除学生信息
    @DeleteMapping
    public ResponseEntity<Void> deleteMsg(@RequestBody CompStudent compStudent){
        compService.removeMsg(compStudent);
        return ResponseEntity.ok().build();
    }
}
