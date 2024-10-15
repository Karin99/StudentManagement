package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト
 */
@Getter
@Setter
public class Student {

    @Size(min = 1, max = 3)
    @Pattern(regexp = "\\d+", message = "IDは数字のみにする必要があります")
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^[\u30A0-\u30FF]+$", message = "全角カタカナのみを入力してください")
    private String kana;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotNull
    private int age;

    @NotBlank
    @Pattern(regexp = "男|女|その他", message = "性別は「男」「女」「その他」のいずれかで選択してください")
    private String gender;

    private String remark;

    private boolean isDeleted;

}
