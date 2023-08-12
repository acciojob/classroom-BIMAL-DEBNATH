package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentController {
   HashMap<String,Student>studentDb=new HashMap<>();
    HashMap<String,Teacher>teacherDb=new HashMap<>();
    HashMap<String,String>studentTeacherDb=new HashMap<>();
    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Student student){
         studentDb.put(student.getName(),student);
        return new ResponseEntity<>("New student added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-teacher")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher){
         teacherDb.put(teacher.getName(), teacher);
        return new ResponseEntity<>("New teacher added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-student-teacher-pair")
    public ResponseEntity<String> addStudentTeacherPair(@RequestParam String student, @RequestParam String teacher){
       studentTeacherDb.put(teacher, student);
        return new ResponseEntity<>("New student-teacher pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-student-by-name/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name){
        Student student = studentDb.get(name); // Assign student by calling service layer method

        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/get-teacher-by-name/{name}")
    public ResponseEntity<Teacher> getTeacherByName(@PathVariable String name){
        Teacher teacher = teacherDb.get(name); // Assign student by calling service layer method

        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping("/get-students-by-teacher-name/{teacher}")
    public ResponseEntity<List<String>> getStudentsByTeacherName(@PathVariable String teacher){
        List<String> students = new ArrayList<>(); // Assign list of student by calling service layer method

        for(String teachers:studentTeacherDb.keySet()){
            if(teachers.equals(teacher)){
                students.add(studentTeacherDb.get(teachers));
            }
        }

        return new ResponseEntity<>(students, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity<List<String>> getAllStudents(){
        List<String> students = new ArrayList<>(); // Assign list of student by calling service layer method

        for(String student:studentDb.keySet()){
            students.add(student);
        }

        return new ResponseEntity<>(students, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-teacher-by-name")
    public ResponseEntity<String> deleteTeacherByName(@RequestParam String teacher){

        if(teacherDb.containsKey(teacher)){
            teacherDb.remove(teacher);
        }
        if(studentTeacherDb.containsKey(teacher)){
            studentTeacherDb.remove(teacher);
        }
        return new ResponseEntity<>(teacher + " removed successfully", HttpStatus.CREATED);
    }
    @DeleteMapping("/delete-all-teachers")
    public ResponseEntity<String> deleteAllTeachers(){
        teacherDb.clear();
        studentTeacherDb.clear();
        return new ResponseEntity<>("All teachers deleted successfully", HttpStatus.CREATED);
    }
}
