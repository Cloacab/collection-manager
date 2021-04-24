package model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rules {
    long leftBorder() default Long.MIN_VALUE;
    long rightBorder() default Long.MAX_VALUE;
    boolean nullable() default false;
    boolean epmtyString() default false;
}
