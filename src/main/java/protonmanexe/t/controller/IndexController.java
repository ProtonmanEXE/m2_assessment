package protonmanexe.t.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/", produces=MediaType.TEXT_HTML_VALUE)
public class IndexController {

    @GetMapping // as I created this controller with a path "/",
                // I have no choice but to create a GetMapping path
                // too
    public String getIndex () {
        return "index";
    }

    @PostMapping // this method is created to allow my html address 
                 // to not show ? when returning to index page
    public String postIndex () {
        return "index";
    }
    
}
