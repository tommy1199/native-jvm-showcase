package javalin.demo;

import javalin.demo.model.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TodoService {

    private List<Todo> todos = new ArrayList<>();

    public TodoService() {
        todos.add(new Todo(1, "Todo 1"));
        todos.add(new Todo(2, "Todo 2"));
        todos.add(new Todo(3, "Todo 3"));
        todos.add(new Todo(4, "Todo 4"));
    }

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
            return todos.remove(todo.get());
        } else {
            return false;
        }
    }

    public boolean update(Todo todo) {
        Optional<Todo> optionalTodo = findById(todo.getId());
        if (optionalTodo.isPresent()) {
            todos.remove(optionalTodo.get());
            todos.add(todo);
            return true;
        } else {
            return false;
        }
    }
}
