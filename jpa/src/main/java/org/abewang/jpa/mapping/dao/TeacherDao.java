package org.abewang.jpa.mapping.dao;

import org.abewang.jpa.mapping.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Abe
 * @Date 2018/8/30.
 */
public interface TeacherDao extends JpaRepository<Teacher, Integer> {
}
