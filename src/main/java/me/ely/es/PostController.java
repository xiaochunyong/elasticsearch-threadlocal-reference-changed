package me.ely.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author <a href="mailto:xiaochunyong@gmail.com">ely</a>
 * @since 2023/5/24
 */
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private ElasticsearchClient client;

    public static Thread currentThread;

    public static String welcome = "hello1";

    @GetMapping
    public Object query() throws IOException {
        currentThread = Thread.currentThread();
        welcome = "hello2";

        SearchResponse<ObjectNode> search = client.search(s -> s
                        .index("post")
                        .query(b -> b
                                .matchAll(b2 -> b2)
                        ),
                ObjectNode.class
        );

        return search.hits().hits();
    }

}
