package org.abewang.jpa.mapping.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 教室实体
 *
 * @Author Abe
 * @Date 2018/8/29.
 */
@Entity
public class Clazz implements Serializable {
    @Id
    private int id;

    @NotNull
    private String name;

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
    // endregion
}
