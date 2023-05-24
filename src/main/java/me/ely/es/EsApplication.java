package me.ely.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author <a href="mailto:xiaochunyong@gmail.com">ely</a>
 * @since 2023/5/24
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);
    }
}
