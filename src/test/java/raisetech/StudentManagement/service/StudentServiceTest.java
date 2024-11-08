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
import raisetech.StudentManagement.exception.CustomNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
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
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処置が適切に呼び出せていること() throws CustomNotFoundException {
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

        // 後処理
    }

    @Test
    void 受講生詳細の一覧検索_NotFound例外が処理されること() {
        List<Student> studentList = new ArrayList<>();

        Assertions.assertThrows(CustomNotFoundException.class, () ->
            sut.searchStudentList());
    }

    @Test
    void 受講生詳細検索_リポジトリの処理が適切に呼び出せていること() throws CustomNotFoundException {
        String id = new String();
        Student student = new Student();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

        sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(id);
        verify(repository, times(1)).searchStudentCourse(student.getId());

    }

    @Test
    void 受講生詳細検索_NotFound例外が処理されること() throws CustomNotFoundException {
        String id = new String();

        Assertions.assertThrows(CustomNotFoundException.class, () ->
            sut.searchStudent(id));
    }

    @Test
    void 受講生登録_リポジトリとコース情報初期設定の処理が適切に呼び出せていること() {
        Student student = new Student();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
        StudentService spy = spy(sut);

        sut.registerStudent(studentDetail);

        verify(repository, times(1)).registerStudent(studentDetail.getStudent());
        verify(spy, times(studentCourseList.size())).initStudentCourse(any(StudentCourse.class), eq(student));
        verify(repository, times(studentCourseList.size())).registerStudentCourse(any(StudentCourse.class));
    }

    @Test
    void 受講生コース情報登録_情報処理が適切に行われていること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();

        sut.initStudentCourse(studentCourse, student);

        Assertions.assertEquals(student.getId(), studentCourse.getStudentId());
        Assertions.assertNotNull(studentCourse.getStartAt());
        Assertions.assertNotNull(studentCourse.getCompleteAt());
        Assertions.assertEquals(studentCourse.getStartAt().plusYears(1), studentCourse.getCompleteAt());
    }

    @Test
    void 受講生詳細の更新_リポジトリが適切に呼び出せていること() throws CustomNotFoundException {
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
    void 受講生詳細の更新_NotFound例外が処理されること() throws CustomNotFoundException {
        StudentDetail studentDetail = new StudentDetail();

        Assertions.assertThrows(CustomNotFoundException.class, () ->
            sut.updateStudent(studentDetail));
    }
}