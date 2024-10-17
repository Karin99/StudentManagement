package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 受講生詳細を受講生情報や受講生コース情報に変換、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

    /**
     * 受講生に紐づく受講生コース情報をマッピングする。
     * 受講生コース情報は、一人の受講生に対して複数存在する可能性があるため、ループを回して受講生詳細情報を組み立てる。
     * @param studentList 受講生一覧
     * @param studentCourseList 受講生コース情報のリスト
     * @return 受講生詳細情報のリスト
     */
    public List<StudentDetail> convertStudentDetails(List<Student> studentList, List<StudentCourse> studentCourseList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : studentList) {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourse> convertStudentCourseList = new ArrayList<>();
            for (StudentCourse studentCourse : studentCourseList) {
                if (student.getId().equals(studentCourse.getStudentId())) {
                    convertStudentCourseList.add(studentCourse);
                }
            }
            studentDetail.setStudentCourseList(convertStudentCourseList);
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
