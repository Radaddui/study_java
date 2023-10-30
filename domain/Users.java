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
@ToString(exclude = {"recipeList_Users", "commentsList_Users", "ReportList_Users"})


@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "users_num")
    @SequenceGenerator(name = "users_num", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "users_num")
    private Long num;

    @Column(name = "uk_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, name = "uk_nickname")
    private String nickName;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date alterDate;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Column(nullable = false)
    private Integer enabled = 1;

    @Column(nullable = false, unique = true, name = "uk_email")
    private String email;


    @OneToMany(
            targetEntity = Recipe.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "fk_users"
    )
    private List<Recipe> recipeList_Users = new ArrayList<>();


    @OneToMany(
            targetEntity = Comments.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "fk_users"
    )
    private List<Comments> commentsList_Users = new ArrayList<>();


    @OneToMany(
            targetEntity = Report.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "fk_users"
    )
    private List<Report> ReportList_Users = new ArrayList<>();

} // end class
