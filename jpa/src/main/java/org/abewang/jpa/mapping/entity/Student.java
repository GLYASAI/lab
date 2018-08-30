package org.abewang.jpa.mapping.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 学生实体
 *
 * @Author Abe
 * @Date 2018/8/29.
 */
@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "students",  // 通过维护端的属性关联
            fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

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

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    // endregion
}
