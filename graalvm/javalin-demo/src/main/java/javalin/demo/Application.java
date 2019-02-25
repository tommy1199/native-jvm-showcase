package javalin.demo;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.crud;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create()
                .disableStartupBanner()
                .start(8080);

        TodoController controller = new TodoController(new TodoService());

        app.routes(() -> {
            crud("todos/:id", controller);
        });
    }
}