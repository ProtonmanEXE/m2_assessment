package protonmanexe.t.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import protonmanexe.t.service.BookService;

@Controller
@RequestMapping(path="/result", produces=MediaType.TEXT_HTML_VALUE)
public class SearchController {

    @Autowired
    BookService bookSvc;

    private final static Logger logging = LoggerFactory.getLogger(SearchController.class);
    
    // start of Task 4
    @GetMapping
    public String getTitle (@RequestParam (required=true) String title, Model model) {

        Map<String, String> mapTitleKey = bookSvc.search(title);
        model.addAttribute("requestedTitle", title);
        logging.info("map all keys > " +mapTitleKey.keySet());
        model.addAttribute("existingTitle", mapTitleKey.keySet());
        
        return "result";
    }
    // end of Task 4


}
