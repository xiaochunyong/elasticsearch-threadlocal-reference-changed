package me.ely.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:xiaochunyong@gmail.com">ely</a>
 * @since 2023/5/24
 */
@Configuration
public class ElasticsearchConfiguration {

    private String host = "localhost";

    private int port = 9200;

    private String username = "elastic";

    private String password = "elastic123456";

    @Bean
    public ElasticsearchTransport elasticsearchTransport() {
        // https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/_basic_authentication.html
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // Create the low-level client
        RestClient restClient = RestClient
                .builder(new HttpHost(host, port))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();

        // Create the transport with a Jackson mapper
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new Jdk8Module());
        // 去掉默认的时间格式
        // objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));
        return transport;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

}
