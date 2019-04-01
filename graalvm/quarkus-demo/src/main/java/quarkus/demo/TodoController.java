package quarkus.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/todos")
public class TodoController {

    private TodoService todoService;

    public TodoController() {
    }

    @Inject
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todos() {
        return Response.ok(todoService.findAll())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response todo(@PathParam("id") int id) {
        return todoService.findById(id)
                .map(todo -> Response.ok(todo)
                        .build())
                .orElse(Response.status(400)
                        .build());
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String body) {
        return Response.ok(todoService.addTodo(body))
                .build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, String body) {
        return todoService.update(id, body)
                .map(todo -> Response.ok(todo)
                        .build())
                .orElse(Response.status(400)
                        .build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (todoService.deleteById(id)) {
            return Response.ok()
                    .build();
        } else {
            return Response.noContent()
                    .build();
        }
    }
}