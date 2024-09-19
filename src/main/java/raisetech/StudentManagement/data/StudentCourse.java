package raisetech.StudentManagement;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
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