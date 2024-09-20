package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourse {

    private String courseId;
    private String studentId;
    private String course;
    private LocalDateTime startAt;
    private LocalDateTime completeAt;

}
