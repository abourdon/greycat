package org.mwdb.task;

@FunctionalInterface
public interface KFunction4<T1, T2, T3, T4, R> {

    R apply(T1 t1, T2 t2, T3 t3, T4 t4) throws Exception;

}