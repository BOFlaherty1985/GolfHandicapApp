package main.java.co.uk.myhandicap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Welcome Controller.
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 02/07/14
 * @project MyHandicapApp
 */
@Controller
public class WelcomeController implements AppController {

    @Override
    @RequestMapping(value="/")
    public ModelAndView handleRequest(ModelAndView mav, Principal principal) {
        mav.setViewName("welcome");
        return mav;
    }

}