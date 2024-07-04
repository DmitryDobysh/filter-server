package com.dobysg.filters.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filterName;

    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Criteria> criteria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Selection selection;

    public enum Selection {
        SELECT_1,
        SELECT_2,
        SELECT_3
    }
}
