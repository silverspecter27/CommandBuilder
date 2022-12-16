package com.github.silverspecter27.commandbuilder.CommandBuilder.Register;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A usefull Annotation to put
 * description of the command.
 * Put on the onCommand Method.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDescription {
    String[] aliases();

    String desc();

    int min() default 0;

    int max() default -1;

    boolean playerOnly() default false;
}
