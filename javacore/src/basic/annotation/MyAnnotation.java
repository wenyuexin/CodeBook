package basic.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target({LOCAL_VARIABLE,METHOD,CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.SOURCE)
@interface MyAnnotation {
	String msg() default "test";
}

@Target({LOCAL_VARIABLE,METHOD,CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation2 {
	String msg() default "test2";
}

@Target({LOCAL_VARIABLE,METHOD,CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation3 {
	String msg() default "test3";
}

@Target({LOCAL_VARIABLE,METHOD,CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation4 {
	String msg() default "test4";
}

