package org.example.textingquest.daos;

import java.util.List;
import java.util.Optional;

public interface DAO<L extends Number, U> {

    boolean update(U o);

    List findAll();
    Object findById(Integer id);

    Object save(U o);
    boolean delete(Integer id);
}
