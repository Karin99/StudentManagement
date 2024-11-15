package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentConverterTest {

    private StudentConverter sut;

    @BeforeEach
    void before(){
        sut = new StudentConverter();
    }

    @Test
    void 正常系_受講生リストと受講生コース情報のリストを渡したときにIDに紐づいた受講生詳細のリストが返ってくること() {
        String id = "999";

        Student student = new Student();
        student.setId(id);
        List<Student> studentList = List.of(student);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(id);
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertEquals(student, actual.getFirst().getStudent());
        assertEquals(studentCourseList, actual.getFirst().getStudentCourseList());
    }

    @Test
    void 正常系_受講生リストと受講生コース情報のリストを渡したときにIDに紐づかない受講生コース情報は除外されること() {
        Student student = new Student();
        student.setId("999");
        List<Student> studentList = List.of(student);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId("111");
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.getFirst().getStudent()).isEqualTo(student);
        assertThat(actual.getFirst().getStudentCourseList()).isEmpty();
    }
}
