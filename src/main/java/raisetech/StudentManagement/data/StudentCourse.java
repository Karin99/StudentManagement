package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 受講生コース情報を扱うオブジェクト
 */
@Getter
@Setter
public class StudentCourse {

    private String courseId;

    private String studentId;

    @NotBlank
    @Pattern(regexp = "ピアノコース|ギターコース|ドラムコース|ヴァイオリンコース|サックスコース|ボーカルコース",
            message = "コースは「ピアノコース」「ギターコース」「ドラムコース」「ヴァイオリンコース」「サックスコース」「ボーカルコース」のいずれかで選択してください")
    private String course;

    private LocalDateTime startAt;

    private LocalDateTime completeAt;

}
