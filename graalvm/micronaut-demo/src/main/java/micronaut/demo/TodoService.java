package micronaut.demo;

import io.micronaut.spring.tx.annotation.Transactional;
import micronaut.demo.model.Todo;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class TodoService {

    @PersistenceContext
    private EntityManager entityManager;

    public TodoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM todos", Todo.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Todo> findById(int id) {
        return entityManager.createNativeQuery("SELECT * FROM todos where id = :id", Todo.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    public Optional<Todo> update(int id, String text) {
        return entityManager.createNativeQuery("UPDATE todos SET content = :content where id = :id RETURNING id, content", Todo.class)
                .setParameter("id", id)
                .setParameter("content", text)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    public Todo add(String title) {
        return (Todo) entityManager.createNativeQuery("INSERT INTO todos (content) VALUES (:content) RETURNING id, content", Todo.class)
                .setParameter("content", title)
                .getSingleResult();
    }

    @Transactional
    public boolean delete(Integer id) {
        Optional<Todo> todo = findById(id);
        if (todo.isPresent()) {
            entityManager.remove(todo.get());
            return true;
        } else {
            return false;
        }
    }
}
