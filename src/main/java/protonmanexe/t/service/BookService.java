package protonmanexe.t.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import static protonmanexe.t.Constants.*;

@Service
public class BookService {

    private final static Logger logging = LoggerFactory.getLogger(BookService.class);

    // start of Task 5
    public Map<String, String> search (String searchTerm) {

        // build url to search by title, getting json object
        final String url = UriComponentsBuilder
                .fromUriString(URL_TITLE)  
                .queryParam("q", searchTerm.trim().replace(" ", "+")) 
                // trim whitespaces and replace spaces with +
                .queryParam("limit", 2)
                .toUriString();
        logging.info("url > " +url);

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) // if bad response
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString())
            );
        final String body = resp.getBody(); // if ok response
        // logging.info("payload: %s".formatted(body));

        Map<String, String> map = new HashMap<>();            
        // convert weather json object into weather object
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray result2 = result.getJsonArray("docs");

            for (JsonValue i : result2) {
                String key = i.asJsonObject().getString("key");
                String title = i.asJsonObject().getString("title");
                map.put(title, key);
            }

        } catch (Exception ex) { }

        logging.info("map all keys > " +map.keySet());

        return map; // return completed map of title as keys and key as values

    }
    // end of Task 5


}
