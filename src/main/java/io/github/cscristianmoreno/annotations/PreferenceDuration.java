package io.github.cscristianmoreno.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface PreferenceDuration {
    int days() default 0;
    int hours() default 2;
    int minutes() default 0;
}
