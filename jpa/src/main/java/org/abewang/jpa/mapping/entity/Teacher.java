package org.abewang.jpa.mapping.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 教师实体
 *
 * @Author Abe
 * @Date 2018/8/29.
 */
@Entity
public class Teacher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_student", joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();

    // region getter & setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // endregion
}
