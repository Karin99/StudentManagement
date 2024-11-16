package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentConverterTest {

    private StudentConverter sut;

    @BeforeEach
    void before() {
        sut = new StudentConverter();
    }

    @Test
    void 正常系_受講生リストと受講生コース情報のリストを渡したときにIDに紐づいた受講生詳細のリストが返ってくること() {
        Student student = new Student("999", "江並浩二", "エナミコウジ", "こーじ",
                "koji@example.com", "奈良県", 36, "男", "特になし", false);
        List<Student> studentList = List.of(student);

        LocalDateTime now = LocalDateTime.now();
        StudentCourse studentCourse = new StudentCourse(
                "999", "999", "ピアノコース", now, now.plusYears(1));
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertEquals(student, actual.getFirst().getStudent());
        assertEquals(studentCourseList, actual.getFirst().getStudentCourseList());
    }

    @Test
    void 正常系_受講生リストと受講生コース情報のリストを渡したときにIDに紐づかない受講生コース情報は除外されること() {
        Student student = new Student("999", "江並浩二", "エナミコウジ", "こーじ",
                "koji@example.com", "奈良県", 36, "男", "特になし", false);
        List<Student> studentList = List.of(student);

        LocalDateTime now = LocalDateTime.now();
        StudentCourse studentCourse = new StudentCourse(
                "999", "111", "ピアノコース", now, now.plusYears(1));
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.getFirst().getStudent()).isEqualTo(student);
        assertThat(actual.getFirst().getStudentCourseList()).isEmpty();
    }
}
