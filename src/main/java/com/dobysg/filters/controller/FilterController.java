package com.dobysg.filters.controller;

import com.dobysg.filters.model.Filter;
import com.dobysg.filters.repository.FilterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/filters")
public class FilterController {

    private final FilterRepository filterRepository;

    @Autowired
    public FilterController(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @PostMapping
    public Filter createFilter(@RequestBody Filter filter) {
        return filterRepository.save(filter);
    }

    @GetMapping
    public List<Filter> getFilters() {
        return filterRepository.findAll();
    }
}
