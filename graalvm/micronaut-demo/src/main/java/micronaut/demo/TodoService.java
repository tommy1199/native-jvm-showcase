package micronaut.demo;

import micronaut.demo.model.Todo;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class TodoService {

    List<Todo> todos = Arrays.asList(
            new Todo(1, "todo 1"),
            new Todo(2, "todo 2"),
            new Todo(3, "todo 3"),
            new Todo(4, "todo 4")
    );

    public List<Todo> findAll() {
        return todos;
    }

    public Optional<Todo> findById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst();
    }

    public Todo addTodo(String title) {
        Todo todo = new Todo(generateId(), title);
        todos.add(todo);
        return todo;
    }

    public int generateId() {
        int lastId = todos.stream()
                .mapToInt(Todo::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }

    public boolean deleteById(Integer id) {
        Optional<Todo> todo = findById(id);
        if (todo.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
