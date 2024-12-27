package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 正常系_受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(get("/studentList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 正常系_受講生詳細の検索が実行できて空の受講生詳細が返ってくること() throws Exception {
        String id = "999";
        mockMvc.perform(get("/student/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudent(id);
    }

    @Test
    void 異常系_受講生詳細の検索でIDに数字以外が入力されたときに異常が発生すること() throws Exception {
        String id = "テスト";
        mockMvc.perform(get("/student/{id}", id))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 正常系_受講生詳細の登録が実行できて空で返ってくること() throws Exception {

        mockMvc.perform(post("/registerStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "student" : {
                                        "name" : "江並公史",
                                        "kana" : "エナミコウジ",
                                        "nickname" : "こーじ",
                                        "email" : "test@example.com",
                                        "address" : "奈良県,奈良市",
                                        "age" : 36,
                                        "gender" : "男",
                                        "remark" : "登録テストです"
                                    },
                                    "studentCourseList" : [
                                        {
                                            "course" : "ピアノコース"
                                        }
                                    ]
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).registerStudent(any(StudentDetail.class));
    }

    @Test
    void 正常系_受講生詳細の更新が実行できて更新完了メッセージが返ってくること() throws Exception {

        mockMvc.perform(put("/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "student" : {
                                        "id" : "999",
                                        "name" : "江並公史",
                                        "kana" : "エナミコウジ",
                                        "nickname" : "こーじ",
                                        "email" : "test@example.com",
                                        "address" : "奈良県,奈良市",
                                        "age" : 36,
                                        "gender" : "男",
                                        "remark" : "更新テストです",
                                        "isDeleted" : true
                                    },
                                    "studentCourseList" : [
                                        {
                                            "courseId" : "99999",
                                            "studentId" : "999",
                                            "course" : "ピアノコース",
                                            "startAt" : "2024-01-01 00:00:00",
                                            "completeAt" : "2025-01-01 00:00:00"
                                        }
                                    ]
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("江並公史さんの情報を更新しました"));

        verify(service, times(1)).updateStudent(any(StudentDetail.class));
    }

    @Test
    void 異常系_受講生詳細の例外APIが実行できてステータスが500で返ってくること() throws Exception {
        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("エラーコード：500\nエラーメッセージ：\nこのAPIは現在利用できません\n"));
    }

    @Test
    void 正常系_入力チェック_受講生詳細の受講生で適切な値を入力したときに異常が発生しないこと() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(0);
    }


    @Test
    void 異常系_入力チェック_受講生詳細の受講生でIDに4文字以上の数字を用いたとき入力チェックに掛かること() {
        Student student = new Student(
                "99999",        //不正な数字5桁のID
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("IDは3桁までにする必要があります");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でIDに数字以外を用いたとき入力チェックに掛かること() {
        Student student = new Student(
                "テスト",      // 不正な文字のID
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("IDは数字のみにする必要があります");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生で名前が空欄だったとき入力チェックに掛かること() {
        Student student = new Student(
                "999",
                null,       //不正な空の名前
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("名前が入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でフリガナが空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                null,     //不正な空のフリガナ
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("フリガナが入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でフリガナが全角カタカナ以外だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "えなみこうじ",     //不正なひらがなのふりがな
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("フリガナは全角カタカナで入力してください");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でニックネームが空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                null,     //不正な空のニックネーム
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("ニックネームが入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でメールアドレスが空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                null,        //不正な空のメールアドレス
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("メールアドレスが入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生でメールアドレスが不正な形式だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@koji@example.com",     //不正な形式のメールアドレス
                "奈良県,奈良市",
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("無効なメールアドレス形式です");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生で住所が空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                null,      //不正な空の住所
                36,
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("住所が入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生で年齢が空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                null,      //不正な空の年齢
                "男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("年齢が入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生で性別が空欄だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                null);       //不正な空の性別

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("性別が入力されていません");
    }

    @Test
    void 異常系_入力チェック_受講生詳細の受講生で性別が期待値以外だったときに入力チェックに掛かること() {
        Student student = new Student(
                "999",
                "江並公史",
                "エナミコウジ",
                "こーじ",
                "test@example.com",
                "奈良県,奈良市",
                36,
                "男性");       //期待値以外の性別

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("性別は「男」「女」「その他」のいずれかで選択してください");
    }

    @Test
    void 異常系_入力チェック_受講生詳細のコース情報でコース名が期待値以外だったときに入力チェックに掛かること() {
        StudentCourse studentCourse = new StudentCourse(
                "ピアノ");      //期待値以外のコース名

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("コースは「ピアノコース」「ギターコース」「ドラムコース」「ヴァイオリンコース」" +
                              "「サックスコース」「ボーカルコース」のいずれかで選択してください");
    }

}
