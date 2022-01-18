package protonmanexe.t.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import protonmanexe.t.model.Book;

@Repository
public class BookRepository {

    @Autowired
    @Qualifier("MyRedis")
    private RedisTemplate<String, Object> template;

    public void save(String key, Book book) {
        template.opsForValue().set(key, book, 10, TimeUnit.MINUTES); 
        // results cached for 10min
    }

    public Optional<Book> get(String key) {
        Book book = (Book) template.opsForValue().get(key);
        return Optional.ofNullable(book);
    }
    
}
