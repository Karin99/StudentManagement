package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.NotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細の一覧検索_正常系_リポジトリとコンバーターの処置が適切に呼び出せていること() throws NotFoundException {
        // 事前準備Before
        List<Student> studentList = List.of(new Student());
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        // 実行
        sut.searchStudentList();

        // 検証
        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    @Test
    void 受講生詳細の一覧検索_異常系_受講生情報がないときにがNotFoundExceptionがスローされること() {
        List<Student> studentList = repository.search();
        when(repository.search()).thenReturn(studentList);

        Assertions.assertThrows(NotFoundException.class, () ->
                sut.searchStudentList());
    }

    @Test
    void 受講生詳細検索_正常系_リポジトリの処理が適切に呼び出せていること() throws NotFoundException {
        String id = new String();
        Student student = new Student();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

        StudentDetail expected = new StudentDetail(student, studentCourseList);

        StudentDetail actual = sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(id);
        verify(repository, times(1)).searchStudentCourse(student.getId());
        assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    }

    @Test
    void 受講生詳細検索_異常系_検索対象のidが存在しない場合にNotFoundExceptionがスローされること() throws NotFoundException {
        String id = new String();

        assertThrows(NotFoundException.class, () ->
                sut.searchStudent(id));
    }

    @Test
    void 受講生登録_正常系_リポジトリとコース情報初期設定の処理が適切に呼び出せていること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

        sut.registerStudent(studentDetail);

        verify(repository, times(1)).registerStudent(student);
        verify(repository, times(studentCourseList.size())).registerStudentCourse(studentCourse);
    }

    @Test
    void 受講生コース情報登録_正常系_情報処理が適切に行われていること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();

        sut.initStudentCourse(studentCourse, student.getId());

        assertEquals(student.getId(), studentCourse.getStudentId());
        assertEquals(LocalDateTime.now().getYear(), studentCourse.getStartAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), studentCourse.getStartAt().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), studentCourse.getStartAt().getDayOfMonth());
        assertEquals(studentCourse.getStartAt().plusYears(1), studentCourse.getCompleteAt());
    }

    @Test
    void 受講生詳細の更新_正常系_リポジトリが適切に呼び出せていること() throws NotFoundException {
        Student student = new Student();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
        when(repository.searchStudent(student.getId())).thenReturn(student);

        sut.updateStudent(studentDetail);

        verify(repository, times(1)).searchStudent(student.getId());
        verify(repository, times(1)).updateStudent(student);
        verify(repository, times(studentCourseList.size())).updateStudentCourse(any(StudentCourse.class));
    }

    @Test
    void 受講生詳細の更新_異常系_更新対象のidが存在しない場合にNotFoundExceptionがスローされること() throws NotFoundException {
        Student student = new Student();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

        assertThrows(NotFoundException.class, () ->
                sut.updateStudent(studentDetail));
    }
}
