package raisetech.StudentManagement.controller.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import raisetech.StudentManagement.exception.NotFoundException;

@Schema(description = "例外")
@ControllerAdvice
public class ExceptionHandler {

    /**
     * 受講生一覧検索、受講生詳細検索、受講生更新で指定した受講生がDBに登録されていない場合の例外処理です。
     * @param ex
     * @return NOT_FOUND
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage("エラーコード：404\nエラーメッセージ：\n" + ex.getMessage() + "\n");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.getErrorMessage());
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
            errors.append("エラーコード：400\nエラーメッセージ：\n")
                    .append(error.getDefaultMessage())
                    .append("\n");
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(errors.toString());

        return new ResponseEntity<>(errorResponse.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 上記の例外処理に該当しない例外を処理します。
     * @param ex
     * @return INTERNAL_SERVER_ERROR
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage("エラーコード：500\nエラーメッセージ：\n" + ex.getMessage() + "\n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.getErrorMessage());
    }
}
