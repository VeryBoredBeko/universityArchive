package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShelfForm {

    @NotBlank(message = "Поле для номера полки не может быть пустым")
    @Size(max = 50, message = "Количество символов в номере полки может составлять до 50")
    private String shelfNumber;

    @Size(max = 255, message = "Количество символов в описании может составлять до 255")
    private String description;

    public Shelf toShelf() {
        Shelf shelf = new Shelf(shelfNumber);
        shelf.setDescription(description);

        return shelf;
    }
}
