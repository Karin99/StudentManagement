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
        expected.add(new Student("1", "佐藤太郎", "サトウタロウ", "たろたん",
                "taro@example.com", "東京都", 20, "男", "特になし", false));
        expected.add(new Student("2", "鈴木次郎", "スズキジロウ", "じろう",
                "jiro.suzuki@example.com", "大阪府", 28, "男", "特になし", false));
        expected.add(new Student("3", "田中花子", "タナカハナコ", "はなこ",
                "hanako.tanaka@example.com", "愛知県", 25, "女", "特になし", false));
        expected.add(new Student("4", "高橋美咲", "タカハシミサキ", "みさき",
                "misaki.takahashi@example.com", "北海道", 32, "女", "特になし", false));
        expected.add(new Student("5", "山田健", "ヤマダケン", "けん",
                "ken.yamada@example.com", "福岡県", 27, "その他", "特になし", false));
        expected.add(new Student("6", "渡辺直美", "ワタナベナオミ", "ナオミ",
                "naomi@example.com", "アメリカ", 36, "女", "特になし", false));

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
        String studentId = "999";       // 存在しない受講生ID

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
        String studentId = "999";       // 存在しない受講生ID

        List<StudentCourse> actual = sut.searchStudentCourse(studentId);

        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    void 正常系_受講生の新規登録を実行できること() {
        Student student = new Student(
                "伊藤一郎",
                "イトウイチロウ",
                "いっくん",
                "ichi@example.com",
                "京都府,京都市",
                40,
                "その他",
                "特になし",
                false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(7);
    }

    @Test
    void 正常系_受講生コースの新規登録を実行できること() {
        LocalDateTime now = LocalDateTime.now();
        String studentId = "6";
        StudentCourse studentCourse = new StudentCourse(
                studentId,
                "ピアノコース",
                now,
                now.plusYears(1));

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
        String newNickname = "オハナ";
        String newEmail = "hanako.yamamoto@example.com";
        String newAddress = "静岡県";
        int newAge = 26;
        String newGender = "その他";
        String newRemark = "氏名・メールアドレス・住所：変更歴あり";
        boolean newDeleted = true;
        Student student = new Student(studentId, newName, newKana, newNickname, newEmail,
                newAddress, newAge, newGender, newRemark, newDeleted);

        sut.updateStudent(student);

        Student actual = sut.searchStudent(studentId);
        assertThat(actual.getName()).isEqualTo(newName);
        assertThat(actual.getKana()).isEqualTo(newKana);
        assertThat(actual.getNickname()).isEqualTo(newNickname);
        assertThat(actual.getEmail()).isEqualTo(newEmail);
        assertThat(actual.getAddress()).isEqualTo(newAddress);
        assertThat(actual.getAge()).isEqualTo(newAge);
        assertThat(actual.getGender()).isEqualTo(newGender);
        assertThat(actual.getRemark()).isEqualTo(newRemark);
        assertThat(actual.isDeleted()).isEqualTo(newDeleted);
    }

    @Test
    void 異常系_対象の受講生IDがstudentsテーブルに存在しないときに何も更新されないこと() {
        String studentId = "999";
        String newName = "山本花子";
        String newKana = "ヤマモトハナコ";
        String newNickname = "オハナ";
        String newEmail = "hanako.yamamoto@example.com";
        String newAddress = "静岡県";
        int newAge = 26;
        String newGender = "その他";
        String newRemark = "氏名・メールアドレス・住所：変更歴あり";
        boolean newDeleted = true;
        Student student = new Student(studentId, newName, newKana, newNickname, newEmail,
                newAddress, newAge, newGender, newRemark, newDeleted);

        sut.updateStudent(student);

        Student actual = sut.searchStudent("3");
        assertThat(actual.getName()).isNotEqualTo(newName);
        assertThat(actual.getKana()).isNotEqualTo(newKana);
        assertThat(actual.getNickname()).isNotEqualTo(newNickname);
        assertThat(actual.getEmail()).isNotEqualTo(newEmail);
        assertThat(actual.getAddress()).isNotEqualTo(newAddress);
        assertThat(actual.getAge()).isNotEqualTo(newAge);
        assertThat(actual.getGender()).isNotEqualTo(newGender);
        assertThat(actual.getRemark()).isNotEqualTo(newRemark);
        assertThat(actual.isDeleted()).isNotEqualTo(newDeleted);
    }

    @Test
    void 正常系_受講生コースIDに紐づく受講生コース情報の更新を実行できること() {
        String courseId = "8";
        String newCourse = "ボーカルコース";
        StudentCourse studentCourse = new StudentCourse(courseId, newCourse);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse("3");
        assertThat(actual.get(1).getCourse()).isEqualTo(newCourse);
    }

    @Test
    void 異常系_対象の受講生コースIDがstudents_coursesテーブルに存在しないときに何も更新されないこと() {
        String courseId = "999";        // 存在しない受講生コースID
        String newCourse = "ボーカルコース";
        StudentCourse studentCourse = new StudentCourse(courseId, newCourse);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse("3");
        assertThat(actual.get(0).getCourse()).isNotEqualTo(newCourse);
        assertThat(actual.get(1).getCourse()).isNotEqualTo(newCourse);
    }
}
