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
@Log4j2
@ToString(exclude = {"fk_users","fk_recipe", "ReportList_Comments"})

@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @Column(name = "comments_num")
    @GeneratedValue(generator = "comments_num")
    @SequenceGenerator(name = "comments_num", initialValue = 1, allocationSize = 1)
    private Long num;

    @Transient
    private Long writer;

    @Transient
    private Long recipe;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus report_status = ReportStatus.REPORT_NOTRECEIVED;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date report_uploaded;

    @UpdateTimestamp
    private Date report_processed;

    @Column(nullable = false)
    private Integer enabled = 1;

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
        log.trace("Comment_setUsers({}) Invoked.", fk_users);
        this.fk_users = fk_users;

        this.fk_users.getCommentsList_Users().add(this);
    } // setUsers



    @ManyToOne(
            targetEntity = Recipe.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "recipe_num",
            nullable = false,
            referencedColumnName = "recipe_num"
    )
    private Recipe fk_recipe;




    public void setRecipe(Recipe fk_recipe){
        log.trace("Comment_setRecipe({}) Invoked.", fk_recipe);
        this.fk_recipe = fk_recipe;

        this.fk_recipe.getCommentsList_Recipe().add(this);
    } // setRecipe

    @OneToMany(
            targetEntity = Report.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "fk_comments"
    )
    private List<Report> ReportList_Comments = new ArrayList<>();






} // end class
