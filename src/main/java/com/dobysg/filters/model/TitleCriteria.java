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
@DiscriminatorValue("TITLE")
public class TitleCriteria extends Criteria {

    @Enumerated(EnumType.STRING)
    @Column(name = "title_condition")
    private TitleCondition condition;

    @JsonProperty("value")
    @Column(name = "title_value")
    private String titleValue;

    public enum TitleCondition {
        STARTS_WITH, ENDS_WITH
    }
}
