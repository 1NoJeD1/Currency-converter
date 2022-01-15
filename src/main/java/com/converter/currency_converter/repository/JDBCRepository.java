package com.converter.currency_converter.repository;

import java.util.List;

public interface JDBCRepository<T, S> {

    public List<S> getAll();

    public S getById(T id);

    public boolean save(S entity);

    public boolean remove(S entity);
}
