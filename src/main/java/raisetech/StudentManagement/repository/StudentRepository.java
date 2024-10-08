package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     * @ return 受講生一覧（全件）
     */
    @Select("SELECT * FROM students")
    List<Student> search();

    /**
     * 受講生の検索を行います。
     * @param id 受講生ID
     * @return 受講生情報
     */
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(String id);

    /**
     * 受講生のコース情報の全件検索を行います。
     * @return 受講生のコース情報（全件）
     */
    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     * @param studentId 受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * 受講生を新規登録します。
     * IDは自動採番を行う。
     * @param student 受講生
     */
    @Insert("INSERT INTO students(name, kana, nickname, email, address, age, gender, remark, is_deleted) " +
            "VALUES(#{name}, #{kana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    /**
     * 受講生コースを新規登録します。
     * コースIDは自動採番を行う。
     * @param studentCourse 受講生コース情報
     */
    @Insert("INSERT INTO students_courses(student_id, course, start_at, complete_at) " +
            "VALUES(#{studentId}, #{course}, #{startAt}, #{completeAt})")
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生情報を更新します。
     * @param student 受講生
     */
    @Update("UPDATE students SET name = #{name}, kana = #{kana}, nickname = #{nickname}, email = #{email}, " +
            "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{deleted} "  +
            "WHERE id = #{id}")
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     * @param studentCourse 受講生コース情報
     */
    @Update("UPDATE students_courses SET course = #{course} WHERE course_id = #{courseId}")
    void updateStudentCourse(StudentCourse studentCourse);

}
