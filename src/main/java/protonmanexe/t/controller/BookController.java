package protonmanexe.t.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import protonmanexe.t.model.Book;
import protonmanexe.t.repository.BookRepository;
import protonmanexe.t.service.BookService;

@Controller
@RequestMapping(path="/book", produces=MediaType.TEXT_HTML_VALUE)
public class BookController {

    @Autowired
    BookService bookSvc;

    @Autowired
    BookRepository bookRepo;

    private final static Logger logging = LoggerFactory.getLogger(SearchController.class);

    // continuing Task 7
    @GetMapping("/{key}")
    public String getContact (@PathVariable(value="key") String key, Model model) {
        
        logging.info("id > " +key);

        // check whether key and book has an existing cache
        Optional<Book> opt = bookRepo.get(key); // retrieve detail from cache

        if (opt.isPresent()) {
            model.addAttribute("BookDetails", opt.get());
            model.addAttribute("status", "True");
            String msg =("Details last cached at " +opt.get().getTimeNow());
            model.addAttribute("msg", msg);
            logging.info("data is from cache");
        }
        // if no cache, attempt to search book details from API
        else {
            try {
                model.addAttribute("BookDetails", bookSvc.getBook(key));
                logging.info("data is from api");
                bookRepo.save(key, bookSvc.getBook(key)); // cache the result
                model.addAttribute("status", "False");            
                logging.info("data saved successfully");
        // if search is not successful, go to error page
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("", "");
                return "error";
            }
        }

        return "detail";
    }
    // end of Task 7

}
