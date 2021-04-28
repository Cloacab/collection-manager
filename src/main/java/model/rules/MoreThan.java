package model.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Rule
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MoreThan {
    long value() default Integer.MIN_VALUE;
}
