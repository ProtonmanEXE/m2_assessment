package protonmanexe.t.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import protonmanexe.t.service.BookService;

@Controller
@RequestMapping(produces=MediaType.TEXT_HTML_VALUE)
public class SearchController {

    @Autowired
    BookService bookSvc;
    
    // start of Task 4
    @GetMapping(path="/result")
    public String getTitle (@RequestParam (required=true) String title, Model model) {

        List<List<String>> mapTitleKey = bookSvc.search(title);
        model.addAttribute("requestedTitle", title);
        model.addAttribute("existingTitle", mapTitleKey);
        
        return "result";
    }
    // end of Task 4

    @GetMapping()
    public String getIndex () {
        
        return "index";
    }

}