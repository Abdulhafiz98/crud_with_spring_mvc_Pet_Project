package org.example.dao;

import java.util.List;

public interface BaseDao<T> {
    T getById(int id);
    List<T> getList();
    boolean delete(int id);

    boolean add(T t);

}
