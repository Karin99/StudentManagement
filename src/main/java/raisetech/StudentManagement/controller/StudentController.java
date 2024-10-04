package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService service;
    private final StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    // 受講生情報の一覧を表示する
    // http://localhost:8080/studentList
    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentCourse> studentCourses = service.searchStudentCourseList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
        return "studentList";
    }

    // 受講生登録画面を表示する
    // http://localhost:8080/newStudent
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(List.of(new StudentCourse()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    // "/newStudent"で入力してもらった情報（StudentDetail studentDetail）を
    // serviceのregisterStudentメソッドに引数として渡して、
    // "/studentList"へリダイレクトする。
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.registerStudent(studentDetail);
        return "redirect:/studentList";
    }

    // "/studentList"で選択された受講生の更新画面を表示する
    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable("id") String id, Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(service.getStudentById(id));
        if (id.equals(studentDetail.getStudent().getId())) {
            model.addAttribute("studentDetail", studentDetail);
        }
        return "updateStudent";
    }

    // 受講生情報更新ページで入力された内容をserviceへ渡す
    // "/studentList"へリダイレクトする。
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }
        service.updateStudent(studentDetail.getStudent());
        return "redirect:/studentList";
    }

}
