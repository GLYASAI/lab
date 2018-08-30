package org.abewang.jpa.mapping;

import org.abewang.jpa.mapping.entity.Student;
import org.abewang.jpa.mapping.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @Author Abe
 * @Date 2018/8/30.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(false)
public class MappingTest {

    @PersistenceContext(unitName = "primaryPersistenceUnit")
    EntityManager em;

    @Test
    void testManyToMany() {
        List<String> teacherNames = new ArrayList<>();
        teacherNames.add("Mr. Zhang");
        teacherNames.add("Miss Wu");

        Set<Student> students = new HashSet<>();
        for (int i = 'A'; i < 'F'; i++) {
            Student student = new Student();
            student.setName("student-" + (char) i);
            em.persist(student);
            students.add(student);
        }

        teacherNames.forEach((name) -> {
            Teacher teacher = new Teacher();
            teacher.setName("Mr. Zhang");
            teacher.setStudents(students);
            em.persist(teacher);
        });

        em.flush();
    }

    @Test
    void testManyToMany2() {
        Teacher teacher = em.find(Teacher.class, 1);
        em.remove(teacher);
        em.flush();
    }
}
