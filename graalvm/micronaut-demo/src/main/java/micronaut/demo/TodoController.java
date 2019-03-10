package micronaut.demo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
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
        if (todoService.delete(id)) {
            return ok();
        } else {
            return noContent();
        }
    }

    @Post(consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<Todo> add(@Body String title) {
        return created(todoService.add(title));
    }

    @Post(value = "/{id}", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<Todo> update(Integer id, @Body String title) {
        return todoService.update(id, title)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.badRequest());
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
