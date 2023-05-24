package me.ely.es;

/**
 * @author <a href="mailto:xiaochunyong@gmail.com">ely</a>
 * @since 2023/5/24
 */
public class UserHolder {

    public static ThreadLocal<String> holder = new ThreadLocal<>();

    public static String welcome = "hello";

}
