package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.search();
    }

    public StudentDetail searchStudent(String id) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(repository.searchStudent(id));
        studentDetail.setStudentsCourses(repository.searchStudentCourses(id));
        return studentDetail;
    }

    public List<StudentCourse> searchStudentCourseList() {
        return repository.searchCourse();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.submitStudent(studentDetail.getStudent());
        for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setStartAt(LocalDateTime.now());
            studentCourse.setCompleteAt(LocalDateTime.now().plusYears(1));
            repository.submitStudentCourse(studentCourse);
        }
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentCourse studentCourse : studentDetail.getStudentsCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            repository.updateStudentCourse(studentCourse);
        }
    }
}
