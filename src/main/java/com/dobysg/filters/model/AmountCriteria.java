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
@DiscriminatorValue("AMOUNT")
public class AmountCriteria extends Criteria {

    @Enumerated(EnumType.STRING)
    @Column(name = "amount_condition")
    private AmountCondition condition;

    @JsonProperty("value")
    @Column(name = "amount_value")
    private String amountValue;

    public enum AmountCondition {
        MORE, LESS
    }
}
