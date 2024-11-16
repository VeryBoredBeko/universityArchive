package com.satbayevUniversity.universityArchive.web;

import com.satbayevUniversity.universityArchive.domain.Shelf;
import com.satbayevUniversity.universityArchive.domain.ShelfForm;
import com.satbayevUniversity.universityArchive.domain.ShelfRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shelves")
public class ShelfController {
    @Autowired
    private ShelfRepository shelfRepository;

    @GetMapping
    public String homeShelf() {
        return "homeShelf";
    }

    @GetMapping("/{id}")
    public String getRecordById(@PathVariable Long id, Model model) {
        Optional<Shelf> shelf = shelfRepository.findById(id);

        if (shelf.isPresent()) {
            model.addAttribute("shelf", shelf.get());
            return "shelf";
        }

        return "redirect:/shelves/list";
    }

    @GetMapping("/list")
    public String listShelves(Model model) {
        List<Shelf> shelves = shelfRepository.findAll();
        model.addAttribute("shelves", shelves);

        return "listShelves";
    }

    @GetMapping("/archivist/create")
    public String showCreateForm(Model model) {
        model.addAttribute("shelfForm", new ShelfForm());

        return "createShelf";
    }

    @GetMapping("/archivist/{id}/edit")
    public String showEditShelfForm(@PathVariable Long id, Model model) {
        Optional<Shelf> shelfOptional = shelfRepository.findById(id);

        if (shelfOptional.isPresent()) {
            model.addAttribute("shelf", shelfOptional.get());
            model.addAttribute("shelfForm", new ShelfForm());

            return "editShelf";
        }

        return "redirect:/shelves/list";
    }

    @PostMapping("/archivist/save")
    public String saveShelf(@Valid @ModelAttribute("shelfForm") ShelfForm shelfForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "createShelf";

        Shelf shelf = shelfForm.toShelf();
        shelfRepository.save(shelf);

        return "redirect:/shelves/list";
    }

    @PostMapping("/archivist/{id}/edit/save")
    public String saveEditedShelf(@PathVariable Long id, @Valid @ModelAttribute("shelfForm") ShelfForm shelfForm,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "editShelf";

        Optional<Shelf> optionalShelf = shelfRepository.findById(id);

        if (optionalShelf.isPresent()) {
            Shelf shelf = optionalShelf.get();

            shelf.setShelfNumber(shelfForm.getShelfNumber());
            shelf.setDescription(shelfForm.getDescription());

            shelfRepository.save(shelf);
        }

        return "redirect:/shelves/list";
    }

    @PostMapping("/archivist/{id}/delete")
    public String deleteRecord(@PathVariable(value = "id") Long id) {
        Optional<Shelf> shelf = shelfRepository.findById(id);
        shelf.ifPresent(value -> shelfRepository.deleteById(id));

        return "redirect:/shelves/list";
    }
}
