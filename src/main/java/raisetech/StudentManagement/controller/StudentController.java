package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import raisetech.StudentManagement.controller.handler.ErrorResponse;
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
     *
     * @param service 受講生サービス
     */
    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行わないものになります。
     *
     * @return 受講生詳細一覧（全件）
     */
    @Operation(
            operationId = "getStudentList",
            summary = "一覧検索",
            tags = {"検索"},
            description = "受講生詳細の一覧検索です。全件検索を行うので、条件指定は行わないものになります。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "200(OK)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StudentDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "404(Not Found)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "500(Internal Server Error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() throws NotFoundException {
        return service.searchStudentList();
    }

    /**
     * 受講生詳細の検索です。
     * IDに基づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生情報
     */
    @Operation(
            operationId = "getStudentList",
            summary = "受講生詳細検索",
            tags = {"検索"},
            description = "受講生詳細の検索です。IDに基づく任意の受講生の情報を取得します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "200(OK)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StudentDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "404(Not Found)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "500(Internal Server Error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 3) @Pattern(regexp = "\\d+") String id) throws NotFoundException {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録を行います。
     *
     * @param studentDetail 受講生詳細情報
     * @return 実行結果
     */
    @Operation(
            operationId = "registerStudent",
            summary = "新規受講生登録",
            tags = {"登録"},
            description = "新規受講生の情報を登録します。\n受講生IDとコースIDは自動採番、論理削除はデフォルトfalse、受講開始日・終了日はタイムスタンプとなっているため、入力不要です。",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"student\": {\"name\": \"佐藤太郎\", \"kana\": \"サトウタロウ\", \"nickname\": \"たろたん\", \"email\": \"taro@example.com\", \"address\": \"東京都,港区\", \"age\": 20, \"gender\": \"男\", \"remark\": \"特になし\"}, " +
                                            "\"studentCourseList\": [{\"course\": \"サックスコース\"}]}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "200(OK)",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "String", example = "新規受講生の登録を行いました")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "400(Bad Request)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "500(Internal Server Error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )

    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います（論理削除）。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @Operation(
            operationId = "updateStudent",
            summary = "受講生情報更新",
            tags = {"更新/論理削除"},
            description = "任意の既存受講生の情報を更新します。キャンセルフラグの更新もここで行います（論理削除）。",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"student\": {\"id\": \"1\", \"name\": \"佐藤太郎\", \"kana\": \"サトウタロウ\", \"nickname\": \"たろたん\", \"email\": \"taro@example.com\", \"address\": \"東京都,港区\", \"age\": 20, \"gender\": \"男\", \"remark\": \"特になし\", \"isDeleted\": false}, " +
                                            "\"studentCourseList\": [{\"course\": \"サックスコース\"}]}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "200(OK)",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "String", example = "受講生情報の更新を行いました")
                            )
                    )
                    ,
                    @ApiResponse(
                            responseCode = "400",
                            description = "400(Bad Request)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "404(Not Found)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "500(Internal Server Error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )

    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) throws NotFoundException {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok(studentDetail.getStudent().getName() + "さんの情報を更新しました。");
    }

    /**
     * 例外を発生させるメソッドです。
     *
     * @return 実行結果
     * @throws Exception 例外
     */
    @Operation(
            operationId = "exception",
            summary = "例外スロー",
            tags = {"例外"},
            description = "例外を発生させます",
            responses = {
                    @ApiResponse(
                            responseCode = "500",
                            description = "500(Internal Server Error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/exception")
    public String exception() throws Exception {
        throw new Exception();
    }
}
