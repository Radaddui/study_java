package org.zerock.myapp.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = {"fk_users", "fk_Categories", "commentsList_Recipe", "ReportList_Recipe"})
@Log4j2

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @Column(name = "recipe_num")
    @GeneratedValue(generator = "recipe_num")
    @SequenceGenerator(name = "recipe_num", initialValue = 1, allocationSize = 1)
    private Long num;

    @Transient
    private Long wirter;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updateDate;


    @Transient
    private Categories Categories;

    @UpdateTimestamp
    @Column(updatable = true, insertable = false)
    private Date processedDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus = ReportStatus.REPORT_NOTRECEIVED;

    @Column
    private Integer enabled = 1;




//    ====================================================================


    @ManyToOne(
            targetEntity = Users.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "users_num",
            nullable = false,
            referencedColumnName = "users_num"
    )
    private Users fk_users;

    public void setUsers(Users fk_users){
        log.trace("Recipe_setUsers({}) Invoked.", fk_users);
        this.fk_users = fk_users;

        this.fk_users.getRecipeList_Users().add(this);
    }


    @ManyToOne(
            targetEntity = Categories.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "Categories_seq",
            nullable = true,
            referencedColumnName = "Categories_seq"
    )
    private Categories fk_Categories;

    public void setCategories(List<Categories> fk_categories) {
        log.trace("setCategories({}) Invoked.", fk_categories);
        this.fk_Categories = fk_Categories;

        for (Categories category : fk_categories) {
            category.getRecipeList_Categories().add(this);
        }
    } // setCategories

//    ====================================================================

    @OneToMany(
            targetEntity = Comments.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "fk_recipe"
    )
    private List<Comments> commentsList_Recipe = new ArrayList<>();


    @OneToMany(
            targetEntity = Report.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "fk_recipe"
    )
    private List<Report> ReportList_Recipe = new ArrayList<>();





//
//
//    @Column(nullable = false)
//    private




} // end class
