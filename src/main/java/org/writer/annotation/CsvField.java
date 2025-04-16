package org.writer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvField {

    /**
     * The name of the column in the CSV file. If not specified, the field name is used.
     *
     * @return the column name
     */
    String name() default "";
}
