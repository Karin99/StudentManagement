package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;
    private StudentRepository repository;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter,StudentRepository repository) {
        this.service = service;
        this.converter = converter;
        this.repository = repository;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentCourse> studentCourses = service.searchStudentCourseList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
        return "studentList";
    }

    // @GetMapping("/studentCourseList")
    // public List<StudentCourse> getStudentCourseList() {
    //     return service.searchStudentCourseList();
    // }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        // 第28回課題：新規受講生情報を登録する処理を実装する
        // おまけ課題：コース情報も一緒に登録できるように実装する。コースは単体でよい。

        service.submitPost(studentDetail.getStudent());
        return "redirect:/studentList";
    }
}
