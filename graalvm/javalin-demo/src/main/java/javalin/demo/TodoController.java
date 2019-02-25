package javalin.demo;

import io.javalin.Context;
import io.javalin.apibuilder.CrudHandler;
import javalin.demo.model.Todo;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TodoController implements CrudHandler {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void delete(@NotNull Context ctx, @NotNull  String pathId) {
        Integer id = Integer.valueOf(pathId);
        if (todoService.deleteById(id)) {
            ctx.status(200);
        } else {
            ctx.status(204);
        }
    }

    @Override
    public void create(@NotNull Context ctx) {
        String content = ctx.body();
        ctx.json(todoService.addTodo(content));
        ctx.status(201);
    }

    @Override
    public void getAll(@NotNull Context ctx) {
        ctx.json(todoService.findAll());
        ctx.status(200);
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull  String pathId) {
        Integer id = Integer.valueOf(pathId);
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (optionalTodo.isPresent()) {
            ctx.json(optionalTodo.get());
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String pathId) {
        Integer id = Integer.valueOf(pathId);
        Todo todo = ctx.bodyAsClass(Todo.class);
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (optionalTodo.isPresent()) {
            todo.setId(optionalTodo.get().getId());
            boolean updated = todoService.update(todo);
            if (updated) {
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    }
}
