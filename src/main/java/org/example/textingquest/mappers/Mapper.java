package org.example.textingquest.mappers;

public interface Mapper<T,F> {
    T mapFrom(F f);
}
