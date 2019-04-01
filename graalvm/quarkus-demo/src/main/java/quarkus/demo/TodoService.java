package quarkus.demo;

import quarkus.demo.model.Todo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TodoService {

    @Inject
    private EntityManager em;

    public TodoService() {
    }

    @Inject
    public TodoService(EntityManager em) {
        this();
        this.em = em;
    }

    public List<Todo> findAll() {
        return em.createQuery("SELECT t FROM Todo t").getResultList();
    }

    public Optional<Todo> findById(int id) {
        return Optional.ofNullable(em.find(Todo.class, id));
    }

    @Transactional
    public Todo addTodo(String title) {
        Todo todo = new Todo(title);
        em.persist(todo);
        return todo;
    }

    @Transactional
    public boolean deleteById(Integer id) {
        Optional<Todo> todo = findById(id);
        if (todo.isPresent()) {
            em.remove(todo.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Optional<Todo> update(int id, String title) {
        Optional<Todo> optionalTodo = findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(title);
            em.persist(todo);
        }
        return optionalTodo;
    }
}
