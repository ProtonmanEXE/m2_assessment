package protonmanexe.t.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Book implements Serializable {
    
    private String title;
    private String description;
    private String excerpt;
    private String timeNow;

    public Book () {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm z");
        String timeNow = now.format(formatter);
        this.timeNow = timeNow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String string) {
        this.excerpt = string;
    }

    public String getTimeNow() {
        return timeNow;
    }
}
