package micronaut.demo;

import io.reactiverse.reactivex.pgclient.PgIterator;
import io.reactiverse.reactivex.pgclient.PgPool;
import io.reactiverse.reactivex.pgclient.PgRowSet;
import io.reactiverse.reactivex.pgclient.Row;
import io.reactiverse.reactivex.pgclient.Tuple;
import micronaut.demo.model.Todo;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class TodoService {

    private final PgPool client;

    public TodoService(PgPool client) {
        this.client = client;
    }

    public List<Todo> findAll() {
        return client.rxQuery("SELECT * FROM todos")
                .map(PgRowSet::iterator)
                .map(this::toTodoList)
                .blockingGet();
    }

    public Optional<Todo> findById(int id) {
        return client.rxPreparedQuery("SELECT FROM todos where id = $1", Tuple.of(id))
                .map(PgRowSet::iterator)
                .map(this::toOptionalTodo)
                .blockingGet();
    }

    public Optional<Todo> update(int id, String text) {
        return client.rxPreparedQuery("UPDATE todos SET content = $1 where id = $2 RETURNING id, content", Tuple.of(text, id))
                .map(PgRowSet::iterator)
                .map(this::toOptionalTodo)
                .blockingGet();
    }

    public Todo add(String title) {
        return client.rxPreparedQuery("INSERT INTO todos (content) VALUES ($1) RETURNING id, content", Tuple.of(title))
                .map(PgRowSet::iterator)
                .map(this::toOptionalTodo)
                .map(Optional::get)
                .blockingGet();
    }

    public boolean delete(Integer id) {
        return client.rxPreparedQuery("DELETE FROM todos where id = $1", Tuple.of(id))
                .map(PgRowSet::size)
                .map(size -> size > 0)
                .blockingGet();
    }

    private List<Todo> toTodoList(PgIterator iterator) {
        List<Todo> todos = new ArrayList<>();
        while (iterator.hasNext()) {
            todos.add(toTodo(iterator.next()));
        }
        return todos;
    }

    private Optional<Todo> toOptionalTodo(PgIterator iterator) {
        if (iterator.hasNext()) {
            return Optional.of(toTodo(iterator.next()));
        } else {
            return Optional.empty();
        }
    }

    private Todo toTodo(Row row) {
        return new Todo(row.getInteger("id"), row.getString("content"));
    }
}
