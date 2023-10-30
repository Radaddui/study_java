package org.zerock.myapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Log4j2
@Data
@ToString(exclude = {"fk_users","fk_recipe","fk_comments"})

@Entity
@Table(name = "report")
public class Report {

    @Id
    @Column(name = "report_num")
    @GeneratedValue(generator = "report_num")
    @SequenceGenerator(name = "report_num", initialValue = 1, allocationSize = 1)
    private Long num;

    @Transient
    private Long writer;

    @Transient
    private Long comments;

    @Transient
    private Long recipe;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus = ReportStatus.REPORT_RECEIVED;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date report_update;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date report_process;


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
        log.trace("Report_setUsers({}) Invoked.", fk_users);
        this.fk_users = fk_users;

        this.fk_users.getReportList_Users().add(this);
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
        log.trace("Report_setRecipe({}) Invoked.", fk_recipe);
        this.fk_recipe = fk_recipe;

        this.fk_recipe.getReportList_Recipe().add(this);
    } // setRecipe



    @ManyToOne(
            targetEntity = Comments.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "comments_num",
            nullable = false,
            referencedColumnName = "comments_num"
    )
    private Comments fk_comments;

    public void setComments(Comments fk_comments){
        log.trace("Comment_setRecipe({}) Invoked.", fk_comments);
        this.fk_comments = fk_comments;

        this.fk_comments.getReportList_Comments().add(this);
    } // setRecipe


} // end class
