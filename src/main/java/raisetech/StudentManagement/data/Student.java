package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.Builder;

/**
 * 受講生を扱うオブジェクト
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Schema(description = "受講生")
public class Student {

    @Size(min = 1, max = 3, message = "IDは3桁までにする必要があります")
    @Pattern(regexp = "^\\d+$", message = "IDは数字のみにする必要があります")
    private String id;

    @NotBlank(message = "名前が入力されていません")
    private String name;

    @NotBlank(message = "フリガナが入力されていません")
    @Pattern(regexp = "^[\u30A0-\u30FF]+$", message = "フリガナは全角カタカナで入力してください")
    private String kana;

    @NotBlank(message = "ニックネームが入力されていません")
    private String nickname;

    @NotBlank(message = "メールアドレスが入力されていません")
    @Email(message = "無効なメールアドレス形式です")
    private String email;

    @NotBlank(message = "住所が入力されていません")
    private String address;

    @NotNull(message = "年齢が入力されていません")
    private Integer age;

    @NotBlank(message = "性別が入力されていません")
    @Pattern(regexp = "男|女|その他", message = "性別は「男」「女」「その他」のいずれかで選択してください")
    private String gender;

    private String remark;

    private boolean isDeleted;

}
