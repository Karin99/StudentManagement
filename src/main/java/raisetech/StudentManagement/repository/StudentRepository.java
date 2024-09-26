package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT * FROM students where isdeleted='0'")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchCourse();

    @Insert("INSERT INTO students (id, name, kana, nickname, email, address, age, gender, remark) " +
            "values (#{id}, #{name}, #{kana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark})")
    void submit(Student student);

    @Insert("INSERT INTO students_courses (course_id, student_id, course, start_at, complete_at) " +
            "values (#{courseId}, #{studentId}, #{course}, #{startAt}, #{completeAt})")
    void submitCourse(StudentCourse studentCourse);

}
