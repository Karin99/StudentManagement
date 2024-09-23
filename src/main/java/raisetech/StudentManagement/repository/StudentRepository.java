package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

/**
 * 受講生情報を扱うリポジトリ。
 *
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

}
