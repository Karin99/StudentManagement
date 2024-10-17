package raisetech.StudentManagement.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import raisetech.StudentManagement.exception.IdNotFoundException;
import raisetech.StudentManagement.exception.StudentNotFoundException;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * 受講生が登録されていない状態で受講生一覧検索を実行したときの例外処理です。
     * @param ex
     * @return NOT_FOUND
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("エラーメッセージ：\n" + ex.getMessage());
    }

    /**
     * 登録されていない受講生IDで受講生詳細検索を実行したときの例外処理です。
     * @param ex
     * @return NOT_FOUND
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> handleIdNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("エラーメッセージ：\n" + ex.getMessage());
    }

    /**
     * 受講生新規登録と受講生更新を実行したとき、バリデーションに失敗した場合の例外処理です。
     * @param ex
     * @return BAD_REQUEST
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append("エラーメッセージ: ")
                    .append(error.getDefaultMessage())
                    .append("\n");
        }

        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 上記の例外処理に該当しない例外を処理します。
     * @param ex
     * @return INTERNAL_SERVER_ERROR
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("エラーメッセージ：\n" + ex.getMessage());
    }
}
