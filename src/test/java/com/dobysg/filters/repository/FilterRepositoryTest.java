package com.dobysg.filters.repository;

import com.dobysg.filters.model.DateCriteria;
import com.dobysg.filters.model.Filter;
import com.dobysg.filters.model.TitleCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FilterRepositoryTest {

    @Autowired
    private FilterRepository filterRepository;

    @Test
    void testCreateAndFindFilter() {
        TitleCriteria criteria1 = new TitleCriteria(TitleCriteria.TitleCondition.STARTS_WITH, "News");
        DateCriteria criteria2 = new DateCriteria(DateCriteria.DateCondition.FROM, "01-06-2023");

        Filter filter = new Filter();
        filter.setFilterName("Recent Titles");
        filter.setCriteria(List.of(criteria1, criteria2));
        filter.setSelection(Filter.Selection.SELECT_3);

        criteria1.setFilter(filter);
        criteria2.setFilter(filter);

        Filter savedFilter = filterRepository.save(filter);

        assertThat(savedFilter.getId()).isNotNull();
        assertThat(savedFilter.getCriteria()).hasSize(2);
        assertThat(savedFilter.getSelection()).isNotNull();

        Filter foundFilter = filterRepository.findById(savedFilter.getId()).orElse(null);

        assertThat(foundFilter).isNotNull();
        assertThat(foundFilter.getFilterName()).isEqualTo("Recent Titles");
        assertThat(foundFilter.getCriteria()).hasSize(2);
        assertThat(foundFilter.getSelection()).isEqualTo(Filter.Selection.SELECT_3);
    }
}

