/*
 * (c) КАЦИТ, 2013. Все права защищены.
 */
package ru.kacit.demo.test.server.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Serge Petunin
 * @created 21.06.13 1:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbunitDataSets {

    String before();

    String after();

}