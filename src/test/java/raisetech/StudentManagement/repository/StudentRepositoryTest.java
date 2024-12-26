package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 正常系_受講生の全件検索を実行できること() {
        List<Student> actual = sut.search();

        List<Student> expected = new ArrayList<>();
        expected.add(sut.searchStudent("1"));
        expected.add(sut.searchStudent("2"));
        expected.add(sut.searchStudent("3"));
        expected.add(sut.searchStudent("4"));
        expected.add(sut.searchStudent("5"));
        expected.add(sut.searchStudent("6"));

        assertThat(actual.size()).isEqualTo(6);
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 正常系_受講生IDに紐づく受講生検索を実行できること() {
        String studentId = "1";

        Student actual = sut.searchStudent(studentId);

        assertThat(actual.getId()).isEqualTo(studentId);
        assertThat(actual.getName()).isEqualTo("佐藤太郎");
    }

    @Test
    void 異常系_対象の受講生IDがstudentsテーブルに存在しないときに空のStudentを返すこと() {
        String studentId = "999";

        Student actual = sut.searchStudent(studentId);

        assertThat(actual).isNull();
    }

    @Test
    void 正常系_受講生コース情報の全件検索を実行できること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(12);
    }

    @Test
    void 正常系_受講生IDに紐づく受講生コース情報の検索を実行できること() {
        String studentId = "1";

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getStudentId()).isEqualTo(studentId);
        assertThat(actual.get(0).getCourseId()).isEqualTo("1");
        assertThat(actual.get(1).getCourseId()).isEqualTo("6");
    }

    @Test
    void 異常系_対象の受講生IDがstudents_coursesテーブルに存在しないときに空のListを返すこと() {
        String studentId = "999";

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    void 正常系_受講生の新規登録を実行できること() {
        Student student = new Student();
        student.setName("伊藤一郎");
        student.setKana("イトウイチロウ");
        student.setNickname("いっくん");
        student.setEmail("ichi@example.com");
        student.setAddress("京都府");
        student.setAge(40);
        student.setGender("その他");
        student.setRemark("特になし");
        student.setDeleted(false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 正常系_受講生コースの新規登録を実行できること() {
        LocalDateTime now = LocalDateTime.now();
        String studentId = "6";
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourse("ピアノコース");
        studentCourse.setStartAt(now);
        studentCourse.setCompleteAt(now.plusYears(1));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);
        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.getLast().getCourse()).isEqualTo("ピアノコース");
        assertThat(actual.getLast().getStartAt().getYear()).isEqualTo(now.getYear());
        assertThat(actual.getLast().getStartAt().getMonth()).isEqualTo(now.getMonth());
        assertThat(actual.getLast().getStartAt().getDayOfMonth()).isEqualTo(now.getDayOfMonth());
        assertThat(actual.getLast().getCompleteAt().getYear()).isEqualTo(now.plusYears(1).getYear());
        assertThat(actual.getLast().getCompleteAt().getMonth()).isEqualTo(now.plusYears(1).getMonth());
        assertThat(actual.getLast().getCompleteAt().getDayOfMonth()).isEqualTo(now.plusYears(1).getDayOfMonth());
    }

    @Test
    void 正常系_受講生IDに紐づく受講生情報の対象項目が正しく更新できること() {
        String studentId = "3";
        String newName = "山本花子";
        String newKana = "ヤマモトハナコ";
        String newEmail = "hanako.yamamoto@example.com";
        String newAddress = "静岡県";
        String newRemark = "氏名・メールアドレス・住所：変更歴あり";
        boolean newDeleted = true;

        Student student = new Student();
        student.setId(studentId);
        student.setName(newName);
        student.setKana(newKana);
        student.setEmail(newEmail);
        student.setAddress(newAddress);
        student.setRemark(newRemark);
        student.setDeleted(newDeleted);

        sut.updateStudent(student);

        Student actual = sut.searchStudent(studentId);
        assertThat(actual.getName()).isEqualTo(newName);
        assertThat(actual.getKana()).isEqualTo(newKana);
        assertThat(actual.getEmail()).isEqualTo(newEmail);
        assertThat(actual.getAddress()).isEqualTo(newAddress);
        assertThat(actual.getRemark()).isEqualTo(newRemark);
        assertThat(actual.isDeleted()).isEqualTo(newDeleted);
    }

    @Test
    void 異常系_対象の受講生IDがstudentsテーブルに存在しないときに何も更新されないこと() {
        String studentId = "999";
        String newName = "山本花子";
        String newKana = "ヤマモトハナコ";
        String newEmail = "hanako.yamamoto@example.com";
        String newAddress = "静岡県";
        String newRemark = "氏名・メールアドレス・住所：変更歴あり";
        boolean newDeleted = true;

        Student student = new Student();
        student.setId(studentId);
        student.setName(newName);
        student.setKana(newKana);
        student.setEmail(newEmail);
        student.setAddress(newAddress);
        student.setRemark(newRemark);
        student.setDeleted(newDeleted);

        sut.updateStudent(student);

        Student actual = sut.searchStudent("3");
        assertThat(actual.getName()).isNotEqualTo(newName);
        assertThat(actual.getKana()).isNotEqualTo(newKana);
        assertThat(actual.getEmail()).isNotEqualTo(newEmail);
        assertThat(actual.getAddress()).isNotEqualTo(newAddress);
        assertThat(actual.getRemark()).isNotEqualTo(newRemark);
        assertThat(actual.isDeleted()).isNotEqualTo(newDeleted);
    }

    @Test
    void 正常系_受講生コースIDに紐づく受講生コース情報の更新を実行できること() {
        String courseId = "8";
        String studentId = "3";
        String newCourse = "ボーカルコース";

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setCourse(newCourse);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);
        assertThat(actual.get(1).getCourse()).isEqualTo(newCourse);
    }

    @Test
    void 異常系_対象の受講生コースIDがstudents_coursesテーブルに存在しないときに何も更新されないこと() {
        String courseId = "999";
        String studentId = "3";
        String newCourse = "ボーカルコース";

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setCourse(newCourse);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);
        assertThat(actual.get(0).getCourse()).isNotEqualTo(newCourse);
        assertThat(actual.get(1).getCourse()).isNotEqualTo(newCourse);
    }
}
