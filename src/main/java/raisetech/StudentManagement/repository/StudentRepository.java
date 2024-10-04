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
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

    /**
     * 全件検索します。
     *
     * @ return　全件検索した受講生情報の一覧
     */

    @Select("SELECT * FROM students where is_deleted='0'")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchCourse();

    @Insert("INSERT INTO students(name, kana, nickname, email, address, age, gender, remark, is_deleted) " +
            "VALUES(#{name}, #{kana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void submitStudent(Student student);

    @Insert("INSERT INTO students_courses(student_id, course, start_at, complete_at) " +
            "VALUES(#{studentId}, #{course}, #{startAt}, #{completeAt})")
    void submitStudentCourse(StudentCourse studentCourse);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student getStudentById(String id);

    @Update("UPDATE students SET name = #{name}, kana = #{kana}, nickname = #{nickname}, email = #{email}, " +
            "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}" +
            "WHERE id = #{id}")
    void updateStudent(Student student);
}
