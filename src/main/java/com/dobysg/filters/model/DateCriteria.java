package com.dobysg.filters.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("DATE")
public class DateCriteria extends Criteria {

    @Enumerated(EnumType.STRING)
    @Column(name = "date_condition")
    private DateCondition condition;

    @JsonProperty("value")
    @Column(name = "date_value")
    private String dateValue;

    public enum DateCondition {
        FROM, TO
    }
}
