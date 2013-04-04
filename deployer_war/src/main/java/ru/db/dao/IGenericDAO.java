package ru.db.dao;

import java.util.List;

public interface IGenericDAO<T> {

    public void insert(T object);

    public List<T> selectALL();

}
