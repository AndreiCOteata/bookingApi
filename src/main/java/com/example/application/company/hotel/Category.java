package com.example.application.company.hotel;

import com.example.application.common.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "Category")
@Table(name = "category", schema = "public")
@SequenceGenerator(name = "category_seq", sequenceName = "category_sequence", allocationSize = 1)
public class Category extends AbstractEntity {

    private static final long serialVersionUID = -7554033807918788198L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "name")
    private CategoryEnum name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private List<Hotel> hotels;
}
