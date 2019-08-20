package work.niter.wecoding.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Set;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.entity.StusBO;
import work.niter.wecoding.service.StudentService;
import work.niter.wecoding.utils.FileUtils;

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
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @GetMapping("/stu-id/{id}")
    public Student checkStudentAccountById(@PathVariable(value = "id",required = false) Integer id) {
        Student result = null;

        try {
            result = studentService.getOne(id);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    @GetMapping("/stu-username/{username}")
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
    public List<Student> findAllStudentsForREST() {
        return studentService.getAllStudentsInService();
    }

    @PostMapping
    public int addAStudentForREST(Student student) {
        return studentService.addOneStudent(student);
    }

    @PostMapping("/stu-id")
    public int updateInfoById(Student student) {
        return studentService.updateStuInfoById(student);
    }

    @PostMapping("/uploadheadimg")
    public Student uploadHeadImg(@RequestBody StusBO stusBO) throws Exception {
        String bace64Data = stusBO.getBaceData();
        String stusBacePath = "F:\\" + stusBO.getStuId() + "stusHeadImg.png";
        FileUtils.base64ToFile(stusBacePath, bace64Data);

        MultipartFile file = FileUtils.fileToMultipart(stusBacePath);
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "png", null);
        String thumbPath = thumbImageConfig.getThumbImagePath(storePath.getPath());
        String bigPath = storePath.getPath();

        Student student = studentService.getOne(stusBO.getStuId());
        student.setStuImg(thumbPath);
        student.setStuBigImg(bigPath);
        studentService.updateStudent(student);
        return student;
    }
}
