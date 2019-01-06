package micronaut.demo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import micronaut.demo.model.Todo;

import java.util.List;

import static io.micronaut.http.HttpResponse.created;
import static io.micronaut.http.HttpResponse.noContent;
import static io.micronaut.http.HttpResponse.ok;

@Controller("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Delete("/{id}")
    public HttpResponse delete(Integer id) {
        if (todoService.deleteById(id)) {
            return ok();
        } else {
            return noContent();
        }
    }

    @Post
    public HttpResponse<Todo> newTodo(String title) {
        return created(todoService.addTodo(title));
    }

    @Get()
    public HttpResponse<List<Todo>> todos() {
        return ok(
                todoService.findAll());
    }

    @Get("/{id}")
    public HttpResponse<Todo> todo(Integer id) {
        return todoService.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.badRequest());
    }
}
