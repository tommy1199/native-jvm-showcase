package micronaut.demo.model;

public class Todo {

    private int id;

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
