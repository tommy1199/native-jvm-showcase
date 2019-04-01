package quarkus.demo.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@RegisterForReflection
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String title;

    public Todo() {
        this(0, "");
    }

    public Todo(String title) {
        this(0, title);
    }

    public Todo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
