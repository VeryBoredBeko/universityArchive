package com.satbayevUniversity.universityArchive.web;

import com.satbayevUniversity.universityArchive.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students/records")
public class StudentRecordController {

    @Autowired
    private StudentRecordRepository studentRecordRepository;

    @Autowired
    private PagingStudentRecordRepository pagingStudentRecordRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public String getRecordById(@PathVariable Long id, Model model, Principal principal) {

        Optional<StudentRecord> recordOptional = studentRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            StudentRecord record = recordOptional.get();

            model.addAttribute("record", record);
            return "userDetailsModal :: detailsModal";
        }

        return "redirect:/students/records/list";
    }

    @GetMapping(value = "/list")
    public String listRecords(@RequestParam(defaultValue = "1") @Min(1) Integer pageNumber,
                              @RequestParam(required = false) String searchText,
                              @RequestParam(required = false) Boolean isResident,
                              Model model) {

        Pageable currentPage = PageRequest.of(pageNumber - 1, 20);
        Page<StudentRecord> records;

        // деректерді іздеу логикасы

        if (searchText != null && !searchText.trim().isEmpty() && isResident != null) {
            records = studentRecordRepository.findBySearchTextAndIsResident(searchText.trim(), isResident, currentPage);
        } else if (searchText != null && !searchText.trim().isEmpty()) {
            records = studentRecordRepository.findBySearchText(searchText.trim(), currentPage);
        } else if (isResident != null) {
            records = studentRecordRepository.findByIsResident(isResident, currentPage);
        } else {
            records = studentRecordRepository.findAll(currentPage);
        }

        List<Shelf> shelves = shelfRepository.findAll();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("records", records);
        model.addAttribute("shelves", shelves);
        model.addAttribute("recordForm", new StudentRecordForm());

        // пагинация барысында нөмірлеу логикасы...

        int totalPages = records.getTotalPages();
        int start = Math.max(1, pageNumber - 1);
        int end = Math.min(totalPages, pageNumber + 1);

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalPages", totalPages);

        return "students/listRecords";
    }


    @GetMapping("/archivist/create")
    public String showCreateForm(Model model) {

        List<Shelf> shelves = shelfRepository.findAll();

        model.addAttribute("shelves", shelves);
        model.addAttribute("recordForm", new StudentRecordForm());

        return "createRecord";
    }

    @GetMapping("/archivist/{id}/edit")
    public String showEditRecordForm(@PathVariable Long id, Model model) {

        Optional<StudentRecord> recordOptional = studentRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            List<Shelf> shelves = shelfRepository.findAll();
            StudentRecord record = recordOptional.get();

            model.addAttribute("shelves", shelves);
            model.addAttribute("recordId", record.getRecordId());

            StudentRecordForm recordForm = new StudentRecordForm(record);

            model.addAttribute("recordForm", recordForm);

            return "fragments/recordEdit :: recordEditModal";
        }

        return "redirect:/students/records/list";
    }

    @PostMapping(value = "/archivist/save")
    public String saveRecord(@Valid @ModelAttribute("recordForm") StudentRecordForm recordForm,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("shelves", shelfRepository.findAll());
            model.addAttribute("recordForm", new StudentRecordForm());

            return "redirect:/records/list";
        }

        StudentRecord record = recordForm.toStudentRecord();
        studentRecordRepository.save(record);

        return "redirect:/students/records/list";
    }

    @PostMapping("/archivist/{id}/delete")
    public String deleteRecord(@PathVariable Long id) {

        System.out.println("Hello!");
        Optional<StudentRecord> record = studentRecordRepository.findById(id);

        record.ifPresent(value -> {
            Shelf shelf = value.getShelf();

            if (shelf != null) shelf.getStudentRecords().remove(value);

            studentRecordRepository.deleteById(id);
        });

        return "redirect:/students/records/list";
    }

    @PostMapping("/archivist/{id}/edit/save")
    public String saveEditedRecord(@PathVariable Long id, @Valid @ModelAttribute("recordForm") StudentRecordForm recordForm,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "editRecord";

        Optional<StudentRecord> recordOptional = studentRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            StudentRecord record = recordOptional.get();

            record.setIin(recordForm.getIin());
            record.setFirstName(recordForm.getFirstName());
            record.setLastName(recordForm.getLastName());
            record.setCountry(recordForm.getCountry());
            record.setRegion(recordForm.getRegion());
            record.setIsResident(recordForm.getIsResident());
            record.setYearOfEnrollment(recordForm.getYearOfEnrollment());
            record.setYearOfGraduation(recordForm.getYearOfGraduation());
            record.setFaculty(recordForm.getFaculty());
            record.setDepartment(recordForm.getDepartment());
            record.setRecordNumber(recordForm.getRecordNumber());
            record.setShelf(recordForm.getShelf());

            studentRecordRepository.save(record);
        }

        return "redirect:/students/records/list";
    }
}
