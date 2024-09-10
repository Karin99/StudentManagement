package raisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

    // Map（名前、年齢）にPOSTで3人分の情報を入れて、出力する
    // 2人目の年齢だけを入れ替える

    private Map<String, String> studentMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/studentInfo")
    public List<String> getStudentInfo() {
        List<String> studentInfo = new ArrayList<>();
        for (Map.Entry<String, String> entry : studentMap.entrySet()) {
            String name = entry.getKey();
            String age = entry.getValue();
            studentInfo.add("名前：" + name + ",年齢：" + age);
        }
        return studentInfo;
    }

    @PostMapping("/studentInfo")
    public void setStudentInfo(String name, String age) {
        studentMap.put(name, age);
    }

    @PostMapping("/updateStudentAge")
    public void updateStudentName(String name, String age) {
        List<String> studentInfo = new ArrayList<>();
        if (studentMap.containsKey(name)) {
            studentMap.put(name, age);
        }
    }
}