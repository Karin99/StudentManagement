package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.NotFoundException;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、硬軟などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

    private final StudentService service;

    /**
     * コンストラクタ
     * @param service 受講生サービス
     */
    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行わないものになります。
     * @return 受講生詳細一覧（全件）
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() throws NotFoundException {
        return service.searchStudentList();
    }

    /**
     * 受講生詳細の検索です。
     * IDに基づく任意の受講生の情報を取得します。
     * @param id 受講生ID
     * @return 受講生情報
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 3) @Pattern(regexp = "\\d+") String id) throws NotFoundException {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録を行います。
     * @param studentDetail 受講生詳細情報
     * @return 実行結果
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います（論理削除）。
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) throws NotFoundException {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok(studentDetail.getStudent().getName() + "さんの情報を更新しました。");
    }

    /**
     * 例外を発生させるメソッドです。
     * @return 実行結果
     * @throws Exception 例外
     */
    @GetMapping("/exception")
    public String exception() throws Exception {
       throw new Exception();
    }
}
