package micronaut.demo;

import micronaut.demo.model.Todo;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Singleton
public class TodoService {

    private static final Table<Record> TODOS_TABLE = table("todos");
    private static final Field<Integer> ID_COLUMN = field("id", Integer.class);
    private static final Field<String> CONTENT_COLUMN = field("content", String.class);

    private DSLContext context;

    public TodoService(DSLContext context) {
        this.context = context;
    }

    public List<Todo> findAll() {
        return context.select(ID_COLUMN, CONTENT_COLUMN)
                .from(TODOS_TABLE)
                .orderBy(ID_COLUMN)
                .fetch()
                .map(this::toTodo);
    }

    public Optional<Todo> findById(int id) {
        return context.select(ID_COLUMN, CONTENT_COLUMN)
                .from(TODOS_TABLE)
                .where(ID_COLUMN.eq(id))
                .fetchOptional()
                .map(this::toTodo);
    }

    public Optional<Todo> update(int id, String text) {
        return context.update(TODOS_TABLE)
                .set(CONTENT_COLUMN, text)
                .where(ID_COLUMN.eq(id))
                .returning(ID_COLUMN, CONTENT_COLUMN)
                .fetchOptional()
                .map(this::toTodo);
    }

    public Todo add(String title) {
        return context.insertInto(TODOS_TABLE, CONTENT_COLUMN)
                .values(title)
                .returning(ID_COLUMN, CONTENT_COLUMN)
                .fetchOptional()
                .map(this::toTodo)
                .get();
    }

    public boolean delete(Integer id) {
        return context.deleteFrom(TODOS_TABLE)
                .where(ID_COLUMN.eq(id))
                .execute() < 0;
    }

    private Todo toTodo(Record record) {
        return new Todo(record.getValue(ID_COLUMN), record.getValue(CONTENT_COLUMN));
    }
}
