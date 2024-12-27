package raisetech.StudentManagement.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 受講生コース情報を扱うオブジェクト
 */
@NoArgsConstructor
@Getter
@Schema(description = "受講生コース情報")
public class StudentCourse {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String courseId;

    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String studentId;

    @NotBlank
    @Pattern(regexp = "ピアノコース|ギターコース|ドラムコース|ヴァイオリンコース|サックスコース|ボーカルコース",
            message = "コースは「ピアノコース」「ギターコース」「ドラムコース」「ヴァイオリンコース」「サックスコース」「ボーカルコース」のいずれかで選択してください")
    private String course;

    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime startAt;

    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime completeAt;

    public static StudentCourse initStudentCourse(StudentCourse studentCourse, String id, LocalDateTime now) {
        studentCourse.setStudentId(id);
        studentCourse.setStartAt(now);
        studentCourse.setCompleteAt(now.plusYears(1));
        return studentCourse;
    }

    public StudentCourse(String courseId, String studentId, String course, LocalDateTime startAt, LocalDateTime completeAt) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.course = course;
        this.startAt = startAt;
        this.completeAt = completeAt;
    }

    public StudentCourse(String course) {
        this.course = course;
    }

    public StudentCourse(String studentId, String course, LocalDateTime startAt, LocalDateTime completeAt) {
        this.studentId = studentId;
        this.course = course;
        this.startAt = startAt;
        this.completeAt = completeAt;
    }

    public StudentCourse(String studentId, LocalDateTime startAt, LocalDateTime completeAt) {
        this.studentId = studentId;
        this.startAt = startAt;
        this.completeAt = completeAt;
    }

    public StudentCourse(String courseId, String course) {
        this.courseId = courseId;
        this.course = course;
    }
}
