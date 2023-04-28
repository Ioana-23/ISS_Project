package project.repo;

import project.domain.Entity;

import java.util.List;

public interface Repository<E extends Entity<Tip>, Tip> {
    void add(E itemToAdd);
    List<E> findAll();
    Tip findId(E itemToFind);
}
