package raisetech.StudentManagement.controller.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "例外発生時のレスポンス")
@Setter
@Getter
public class ErrorResponse {

    private String errorMessage;

    public ErrorResponse() {
        this.errorMessage = errorMessage;
    }
}
