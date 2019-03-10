package micronaut.demo;

import com.github.jasync.sql.db.Connection;
import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.ResultSet;
import com.github.jasync.sql.db.RowData;
import micronaut.demo.model.Todo;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Singleton
public class TodoService {

    private final Connection client;

    public TodoService(Connection client) {
        this.client = client;
    }

    public List<Todo> findAll() {
        return client.sendQuery("SELECT * FROM todos")
                .thenApply(QueryResult::getRows)
                .thenApply(this::toTodoList)
                .join();
    }

    public Optional<Todo> findById(int id) {
        return client.sendPreparedStatement("SELECT FROM todos where id = ?", asList(id))
                .thenApply(QueryResult::getRows)
                .thenApply(this::toOptionalTodo)
                .join();
    }

    public Optional<Todo> update(int id, String text) {
        return client.sendPreparedStatement("UPDATE todos SET content = ? where id = ? RETURNING id, content", asList(text, id))
                .thenApply(QueryResult::getRows)
                .thenApply(this::toOptionalTodo)
                .join();
    }

    public Todo add(String title) {
        return client.sendPreparedStatement("INSERT INTO todos (content) VALUES (?) RETURNING id, content", asList(title))
                .thenApply(QueryResult::getRows)
                .thenApply(this::toOptionalTodo)
                .thenApply(Optional::get)
                .join();
    }

    public boolean delete(Integer id) {
        return client.sendPreparedStatement("DELETE FROM todos where id = ?", asList(id))
                .thenApply(QueryResult::getRowsAffected)
                .thenApply(rowsAffected -> rowsAffected > 0)
                .join();
    }

    private List<Todo> toTodoList(ResultSet resultSet) {
        return resultSet.stream()
                .map(this::toTodo)
                .collect(toList());
    }

    private Optional<Todo> toOptionalTodo(ResultSet resultSet) {
        return resultSet.stream()
                .findFirst()
                .map(this::toTodo);
    }

    private Todo toTodo(RowData row) {
        return new Todo(row.getInt("id"), row.getString("content"));
    }
}
