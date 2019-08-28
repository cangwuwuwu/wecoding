package work.niter.wecoding.controller;

import java.util.List;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.service.AccountService;
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
    private StudentService studentService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @GetMapping("/id/{id}")
    public Student checkStudentAccountById(@PathVariable(value = "id",required = false) String id) {
        Student result = null;

        try {
            result = studentService.getOne(id);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    @GetMapping("/username/{username}")
    public Student checkStudentAccountByUsername(@PathVariable(value = "username",required = false) String username) {
        Student result = null;
        try {
            result = studentService.getByUsername(username);
        } catch (SpelEvaluationException var4) {
            System.out.println("用户名 " + username + " 正在被注册");
        }

        return result;
    }

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.getAllStudentsInService();
    }

    @PostMapping
    public int addOneStudent(Student student) {
        return studentService.addOneStudent(student);
    }

    @PutMapping
    public int updateInfoById(Student student) {
        return studentService.updateStuInfoById(student);
    }

    /*@PostMapping("/uploadheadimg1")
    public Student uploadHeadImgBase(@RequestBody StusBO stusBO) throws Exception {
        String bace64Data = stusBO.getBaceData();
        String stusBacePath = "F:\\" + stusBO.getStuId() + "stusHeadImg.png";
        FileUtils.base64ToFile(stusBacePath, bace64Data);

        MultipartFile file = FileUtils.fileToMultipart(stusBacePath);
    }*/

    @PostMapping("/uploadheadimg")
    public Student uploadHeadImgBlob(@RequestParam MultipartFile file,
                                     @RequestParam String stuId) throws Exception {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "png", null);
        String bigPath = storePath.getPath();
        String thumbPath = thumbImageConfig.getThumbImagePath(storePath.getPath());

        Student student = new Student();
        student.setStuId(stuId);
        student.setStuImg(thumbPath.replaceAll("M00/00/00/", ""));
        student.setStuBigImg(bigPath.replaceAll("M00/00/00/", ""));
        studentService.updateStuInfoById(student);
        return student;
    }

    @PutMapping("/account")
    public int updateMyPassword(Account account, @RequestParam String newPassword) {
        return accountService.updateAccountPassword(account, newPassword);
    }
}
