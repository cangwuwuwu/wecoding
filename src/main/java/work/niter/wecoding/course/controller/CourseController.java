package work.niter.wecoding.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.course.entity.Apply;
import work.niter.wecoding.course.entity.Course;
import work.niter.wecoding.course.service.CourseService;

import java.util.List;
import java.util.Map;

/**
 * @Author: Cangwu
 * @Date: 2019/10/11 17:00
 * @Description:
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCourseList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        return ResponseEntity.ok(courseService.selectAllCourseList(page, size));
    }

    @PutMapping("/star")
    public ResponseEntity<Void> starAndCancel(
            @RequestBody Course course) {
        courseService.updateStarRedisMethod(course);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/apply")
    public ResponseEntity<Void> applyCourse(
            @RequestBody Apply apply) {
        courseService.insertApplyMethod(apply);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/addPlay/{id}")
    public ResponseEntity<Void> addPlayNum(@PathVariable Integer id) {
        courseService.incrPlayNum(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/apply/{id}")
    public ResponseEntity<List<Apply>> findCourseApplyList(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getApplyListMethod(id));
    }
}
