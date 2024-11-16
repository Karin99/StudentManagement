package raisetech.StudentManagement.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentCourseTest {

    @Test
    void 正常系_受講生コース情報の初期値設定ができること() {
        String id = "999";
        StudentCourse studentCourse = new StudentCourse();
        LocalDateTime now = LocalDateTime.now();

        StudentCourse.initStudentCourse(studentCourse, id, now);

        assertEquals(id, studentCourse.getStudentId());
        assertEquals(now.getYear(), studentCourse.getStartAt().getYear());
        assertEquals(now.getMonth(), studentCourse.getStartAt().getMonth());
        assertEquals(now.getDayOfMonth(), studentCourse.getStartAt().getDayOfMonth());
        assertEquals(studentCourse.getStartAt().plusYears(1), studentCourse.getCompleteAt());
    }
}
