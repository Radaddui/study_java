package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@NoArgsConstructor


@RequestMapping("/report/*")
@Controller
public class ReportController {


    @GetMapping("/report")
    void goReport(){
        log.trace("goReport() invoked.");
    
    } // goReport
    

} // end class
