package org.example.textingquest.daos;

import java.util.List;
import java.util.Optional;

public interface DAO<L extends Number, U> {

    boolean update(U o);

    List findAll();
    Optional findById(U id);

    Object save(U o);
    boolean delete(U id);
}
