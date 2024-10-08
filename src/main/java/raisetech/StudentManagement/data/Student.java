package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト
 */
@Getter
@Setter
public class Student {

    private String id;
    private String name;
    private String kana;
    private String nickname;
    private String email;
    private String address;
    private int age;
    private String gender;
    private String remark;
    private boolean isDeleted;

}
