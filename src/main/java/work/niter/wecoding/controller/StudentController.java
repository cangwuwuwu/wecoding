package work.niter.wecoding.controller;

import java.util.List;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.entity.UserList;
import work.niter.wecoding.service.CompService;
import work.niter.wecoding.service.StudentService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:18
 * @Description:
 */
@RestController
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    private CompService compService;
    @Autowired
    private StudentService studentService;

    /**
     * 根据学号查询条数
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Integer> checkStudentAccountById(
            @PathVariable(value = "id",required = false) String id) {
        int result = 0;
        try {
            result = compService.getStudentCountById(id);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/current")
    public ResponseEntity<CompStudent> findCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CompStudent student = new CompStudent();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            student =  compService.getStudentById(userDetails.getUsername());
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public List<CompStudent> findAllStudents() {
        return compService.findAllStuMsg();
    }


    @PutMapping
    public ResponseEntity<Void> updateInfoById(@RequestBody CompStudent student) {
        compService.updateStuInfoById(student);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getPart")
    public ResponseEntity<UserList> toSignUpPage1() {
        return ResponseEntity.ok(studentService.findEightStudents());
    }
}
