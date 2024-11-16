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
@RequestMapping("/employees/records")
public class EmployeeRecordController {

    @Autowired
    private EmployeeRecordRepository employeeRecordRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public String getRecordById(@PathVariable Long id, Model model, Principal principal) {

        Optional<EmployeeRecord> recordOptional = employeeRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            EmployeeRecord record = recordOptional.get();
            model.addAttribute("record", record);

            return "fragments/employeeDetailsModal :: employeeDetailsModal";
        }

        return "redirect:/employees/records/list";
    }

    @GetMapping("/list")
    public String listRecords(@RequestParam(defaultValue = "1") @Min(1) Integer pageNumber,
                              @RequestParam(required = false) String searchText,
                              Model model) {

        Pageable currentPage = PageRequest.of(pageNumber - 1, 1);
        Page<EmployeeRecord> records;

        if (searchText != null && !searchText.trim().isEmpty()) {
            records = employeeRecordRepository.findRecordBySearchText(searchText.trim(), currentPage);
            model.addAttribute("searchText", searchText.trim());
        } else {
            records = employeeRecordRepository.findAll(currentPage);
        }

        List<Shelf> shelves = shelfRepository.findAll();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("records", records);
        model.addAttribute("shelves", shelves);
        model.addAttribute("recordForm", new EmployeeRecordForm());

        int totalPages = records.getTotalPages();
        int start = Math.max(1, pageNumber - 1);
        int end = Math.min(totalPages, pageNumber + 1);

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalPages", totalPages);

//        List<EmployeeRecord> records = employeeRecordRepository.findAll();
//        List<Shelf> shelves = shelfRepository.findAll();
//
//        model.addAttribute("records", records);
//        model.addAttribute("shelves", shelves);
//        model.addAttribute("recordForm", new EmployeeRecordForm());

        return "employees/listRecords";
    }

    @GetMapping("/archivist/create")
    public String showCreateForm(Model model) {

        List<Shelf> shelves = shelfRepository.findAll();

        model.addAttribute("shelves", shelves);
        model.addAttribute("recordForm", new EmployeeRecordForm());

        return "redirect:/";
    }

    @GetMapping("/archivist/{id}/edit")
    public String showEditRecordForm(@PathVariable Long id, Model model) {

        Optional<EmployeeRecord> recordOptional = employeeRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            List<Shelf> shelves = shelfRepository.findAll();
            EmployeeRecord record = recordOptional.get();

            model.addAttribute("shelves", shelves);
            model.addAttribute("recordId", record.getRecordId());

            EmployeeRecordForm recordForm = new EmployeeRecordForm(record);

            model.addAttribute("recordForm", recordForm);

            return "fragments/employeeRecordEdit :: recordEditModal";
        }

        return "redirect:/";
    }

    @PostMapping("/archivist/save")
    public String saveRecord(@Valid @ModelAttribute("recordForm") EmployeeRecordForm recordForm,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("shelves", shelfRepository.findAll());
            model.addAttribute("recordForm", new EmployeeRecordForm());

            return "redirect:/employees/records/list";
        }

        EmployeeRecord record = recordForm.toEmployeeRecord();
        employeeRecordRepository.save(record);

        return "redirect:/employees/records/list";
    }

    @PostMapping("/archivist/{id}/delete")
    public String deleteRecord(@PathVariable Long id) {

        Optional<EmployeeRecord> record = employeeRecordRepository.findById(id);

        record.ifPresent(value -> {
            Shelf shelf = value.getShelf();

            if (shelf != null) shelf.getStudentRecords().remove(value);
            employeeRecordRepository.deleteById(id);
        });

        return "redirect:/employees/records/list";
    }


    @PostMapping("/archivist/{id}/edit/save")
    public String saveEditedRecord(@PathVariable Long id, @Valid @ModelAttribute("recordForm") EmployeeRecordForm recordForm,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "redirect:/employees/records/list";

        Optional<EmployeeRecord> recordOptional = employeeRecordRepository.findById(id);

        if (recordOptional.isPresent()) {
            EmployeeRecord record = recordOptional.get();

            record.setIin(recordForm.getIin());
            record.setFirstName(recordForm.getFirstName());
            record.setLastName(recordForm.getLastName());
            record.setCountry(recordForm.getCountry());
            record.setRegion(recordForm.getRegion());
            record.setIsResident(recordForm.getIsResident());
            record.setYearOfHire(recordForm.getYearOfHire());
            record.setYearOfTermination(recordForm.getYearOfTermination());
            record.setFaculty(recordForm.getFaculty());
            record.setDepartment(recordForm.getDepartment());
            record.setRecordNumber(recordForm.getRecordNumber());
            record.setShelf(recordForm.getShelf());

            employeeRecordRepository.save(record);
        }

        return "redirect:/employees/records/list";
    }
}
