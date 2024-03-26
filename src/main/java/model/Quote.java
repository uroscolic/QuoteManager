package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    private String author;
    private String quote;

    public String toString() {
        return author + ": \"" + quote + "\"";
    }
}
