package protonmanexe.t.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import protonmanexe.t.model.Book;

import static protonmanexe.t.Constants.*;

@Service
public class BookService {

    private final static Logger logging = LoggerFactory.getLogger(BookService.class);

    // start of Task 5
    public List<List<String>> search (String searchTerm) {

        // build url to search by title, getting json object
        final String url = UriComponentsBuilder // url to search by title
                .fromUriString(URL_TITLE)  
                .queryParam("q", searchTerm.trim().replace(" ", "+")) 
                // trim whitespaces and replace spaces with +
                .queryParam("limit", 20) // return a max. of 20 books
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

        List<List<String>> store = new ArrayList<>();            
        // convert weather json object list of titles and keys
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray result2 = result.getJsonArray("docs");

            for (JsonValue i : result2) {
                List<String> str = new ArrayList<>();  
                String s = i.asJsonObject().getString("key"); 
      
                str.add(s.replaceAll("/works/", "")); // remove /works/
                str.add(i.asJsonObject().getString("title"));
                store.add(str);
            }
        } catch (Exception ex) { }

        return store; // return completed list of key as 1st element and
                      // title as 2nd element 

    }
    // end of Task 5

    // continuation of Task 6
    public Book getBook (String key) {

        // build url to search by key, getting json object
        final String url = URL_BOOK.concat(key).concat(".json");
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

        Book book = new Book();
        // convert book json object into details
        InputStream is = new ByteArrayInputStream(body.getBytes());
        final JsonReader reader = Json.createReader(is);
        final JsonObject result = reader.readObject();

        // if there are missing fields, set msg 
        try {
            book.setTitle(result.getString("title"));
        } catch (NullPointerException e) {
            book.setTitle("Not available");
        }

        try {
            book.setDescription(result.getString("description"));
        } catch (NullPointerException e) {
            book.setDescription("Not available");
        }

        try {
            book.setExcerpt(result.getString("excerpt"));
        } catch (NullPointerException e) {
            book.setExcerpt("Not available");
        }

        return book; // return book details

    }
    // end of Task 6

}