package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        // 検索処理
        // 絞り込みをする。年齢が30代の人のみを抽出する。
        // 抽出したリストをコントローラーに返す
        List<Student> studentList = repository.search();
        List<Student> students30sList = new ArrayList<>();

        for (Student student : studentList) {
            int age = student.getAge();
            if ( 29< age && age <40 ) {
                students30sList.add(student);
            }
        }
        return students30sList;
    }

    public List<StudentCourse> searchStudentCourseList() {
        // 絞り込み検索で「Washokuコース」のコース情報のみを抽出する。
        // 抽出したリストをコントローラーに返す。

        List<StudentCourse> studentCourseList = repository.searchCourse();
        List<StudentCourse> studentsWashokuCourse = new ArrayList<>();

        for (StudentCourse studentCourse : studentCourseList){
            String course = studentCourse.getCourse();
            if (course.equals("Washoku")){
                studentsWashokuCourse.add(studentCourse);
            }
        }
        return studentsWashokuCourse;
    }
}
