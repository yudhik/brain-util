package com.brainmaster.util;

import java.io.Serializable;

public interface CrudOperation<T, ID extends Serializable> {
	public abstract T save(T paramT);

	public abstract Iterable<T> save(Iterable<? extends T> paramIterable);

	public abstract T findOne(ID paramID);

	public abstract boolean exists(ID paramID);

	public abstract Iterable<T> findAll();

	public abstract long count();

	public abstract void delete(ID paramID);

	public abstract void delete(T paramT);

	public abstract void delete(Iterable<? extends T> paramIterable);

	public abstract void deleteAll();
}
