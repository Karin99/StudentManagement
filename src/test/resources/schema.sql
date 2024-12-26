CREATE TABLE IF NOT EXISTS students
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  kana VARCHAR(100) NOT NULL,
  nickname VARCHAR(100) DEFAULT NULL,
  email VARCHAR(255) NOT NULL,
  address VARCHAR(100) DEFAULT NULL,
  age INT DEFAULT NULL,
  gender VARCHAR(20) DEFAULT NULL,
  remark VARCHAR(999) DEFAULT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course VARCHAR(100) NOT NULL,
  start_at timestamp NULL DEFAULT NULL,
  complete_at timestamp NULL DEFAULT NULL,
  CONSTRAINT students_courses_ibfk_1 FOREIGN KEY (student_id) REFERENCES students (id)
);
