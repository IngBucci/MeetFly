package sample.mysql;

import java.util.List;

public interface DAO <T>{
    List<T> select(T a) throws DAOException;
    void update(T a) throws DAOException;
    void insert(T a) throws DAOException;
    void delete(T a) throws DAOException;
}
