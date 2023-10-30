package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@NoArgsConstructor


@RequestMapping("/help/*")
@Controller
public class HelpController {


    @GetMapping("/help")
    void goHelp(){
        log.trace("goHelp() invoked.");
    
    } // goHelp
    

} // end class
