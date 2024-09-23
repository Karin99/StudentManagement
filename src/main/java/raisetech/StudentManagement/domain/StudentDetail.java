package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

@Getter
@Setter
public class StudentDetail {

    private Student student;
    private List<StudentCourse> studentsCourses;
}
