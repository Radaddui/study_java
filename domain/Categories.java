package org.zerock.myapp.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Data
@ToString(exclude = "recipeList_Categories")

@Table(name = "Categories")
@Entity
public class Categories {
    @Id
    @Column(name = "Categories_seq")
    @SequenceGenerator(
            name = "Categories_generator",
            sequenceName = "Categories_seq",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(
            generator =  "Categories_generator",
            strategy = GenerationType.SEQUENCE)
    private Integer num;

    private String name;




    @OneToMany(
            targetEntity = Recipe.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "fk_Categories"
    )
    private List<Recipe> recipeList_Categories = new ArrayList<>();

} // end class
