package raisetech.StudentManagement.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 受講生コース情報を扱うオブジェクト
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String courseId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String studentId;

    @NotBlank
    @Pattern(regexp = "ピアノコース|ギターコース|ドラムコース|ヴァイオリンコース|サックスコース|ボーカルコース",
            message = "コースは「ピアノコース」「ギターコース」「ドラムコース」「ヴァイオリンコース」「サックスコース」「ボーカルコース」のいずれかで選択してください")
    private String course;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime startAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime completeAt;

    public static StudentCourse initStudentCourse(StudentCourse studentCourse, String id, LocalDateTime now) {
        studentCourse.setStudentId(id);
        studentCourse.setStartAt(now);
        studentCourse.setCompleteAt(now.plusYears(1));
        return studentCourse;
    }
}
