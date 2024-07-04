package com.dobysg.filters.controller;

import com.dobysg.filters.model.*;
import com.dobysg.filters.repository.FilterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class FilterControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilterRepository filterRepository;

    @BeforeEach
    public void setUp() {
        filterRepository.deleteAll();
    }


    @Test
    void testCreateFilter() throws Exception {
        String filterJson = """
                {
                    "filterName": "Recent Titles",
                    "criteria": [
                        {"field": "TITLE", "condition": "STARTS_WITH", "value": "News"},
                        {"field": "DATE", "condition": "FROM", "value": "01-06-2023"}
                    ],
                    "selection": "SELECT_3"
                }
                """;

        MvcResult result = mockMvc.perform(post("/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filterJson))
                .andExpect(status().isOk())
                .andReturn();

        Filter createdFilter = objectMapper.readValue(result.getResponse().getContentAsString(), Filter.class);

        assertThat(createdFilter).isNotNull();
        assertThat(createdFilter.getId()).isNotNull();
        assertThat(createdFilter.getFilterName()).isEqualTo("Recent Titles");
        assertThat(createdFilter.getCriteria()).hasSize(2);

        TitleCriteria titleCriteria = (TitleCriteria) createdFilter.getCriteria().stream()
                .filter(criteria -> criteria instanceof TitleCriteria)
                .findFirst()
                .orElse(null);
        assertThat(titleCriteria).isNotNull();
        assertThat(titleCriteria.getCondition()).isEqualTo(TitleCriteria.TitleCondition.STARTS_WITH);

        DateCriteria dateCriteria = (DateCriteria) createdFilter.getCriteria().stream()
                .filter(criteria -> criteria instanceof DateCriteria)
                .findFirst()
                .orElse(null);
        assertThat(dateCriteria).isNotNull();
        assertThat(dateCriteria.getCondition()).isEqualTo(DateCriteria.DateCondition.FROM);

        assertThat(createdFilter.getSelection().name()).isEqualTo("SELECT_3");
    }

    @Test
    void testGetFilters() throws Exception {
        TitleCriteria titleCriteria = new TitleCriteria(TitleCriteria.TitleCondition.STARTS_WITH, "News");
        DateCriteria dateCriteria = new DateCriteria(DateCriteria.DateCondition.FROM, "01-06-2023");

        Filter filter = new Filter();
        filter.setFilterName("Recent Titles");
        filter.setCriteria(List.of(titleCriteria, dateCriteria));
        filter.setSelection(Filter.Selection.SELECT_3);

        titleCriteria.setFilter(filter);
        dateCriteria.setFilter(filter);

        filterRepository.save(filter);

        MvcResult result = mockMvc.perform(get("/filters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Filter[] filters = objectMapper.readValue(result.getResponse().getContentAsString(), Filter[].class);

        assertThat(filters).hasSize(1);
        Filter retrievedFilter = filters[0];
        assertThat(retrievedFilter.getFilterName()).isEqualTo("Recent Titles");
        assertThat(retrievedFilter.getCriteria()).hasSize(2);

        TitleCriteria retrievedTitleCriteria = (TitleCriteria) retrievedFilter.getCriteria().stream()
                .filter(criteria -> criteria instanceof TitleCriteria)
                .findFirst()
                .orElse(null);
        assertThat(retrievedTitleCriteria).isNotNull();
        assertThat(retrievedTitleCriteria.getCondition()).isEqualTo(TitleCriteria.TitleCondition.STARTS_WITH);

        DateCriteria retrievedDateCriteria = (DateCriteria) retrievedFilter.getCriteria().stream()
                .filter(criteria -> criteria instanceof DateCriteria)
                .findFirst()
                .orElse(null);
        assertThat(retrievedDateCriteria).isNotNull();
        assertThat(retrievedDateCriteria.getCondition()).isEqualTo(DateCriteria.DateCondition.FROM);

        assertThat(retrievedFilter.getSelection().name()).isEqualTo("SELECT_3");
    }
}
