package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 受講生を扱うオブジェクト
 */
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

    public Student(String id, String name, String kana, String nickname, String email, String address, Integer age, String gender, String remark, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.kana = kana;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.remark = remark;
        this.isDeleted = isDeleted;
    }

    public Student(String id, String name, String kana, String nickname, String email, String address, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.kana = kana;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }

    public Student(String name, String kana, String nickname, String email, String address, Integer age, String gender, String remark, boolean isDeleted) {
        this.name = name;
        this.kana = kana;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.remark = remark;
        this.isDeleted = isDeleted;
    }
}
