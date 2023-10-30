package org.zerock.myapp.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Log4j2
@ToString(exclude = "fk_admins")

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(generator = "notice_num")
    @SequenceGenerator(name = "notice_num", initialValue = 1, allocationSize = 1)
    private Long num;

    @Column(nullable = false)


    @Transient
    private Long writer;


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




    @ManyToOne(
            targetEntity = Admins.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(name = "notice_writer", referencedColumnName = "admin_num", nullable = false)
    private Admins fk_admins;

    public void setAdmins(Admins fk_admins){
        log.trace("setAdmins({}) Invoked.", fk_admins);
        this.fk_admins = fk_admins;
        this.fk_admins.getNoticeList_Admins().add(this);
    } // setAdmins




} // end class
