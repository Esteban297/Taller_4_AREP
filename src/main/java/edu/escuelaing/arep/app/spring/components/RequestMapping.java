package edu.escuelaing.arep.app.spring.components;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value();
}
