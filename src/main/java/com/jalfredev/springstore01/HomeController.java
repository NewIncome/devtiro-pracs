package com.jalfredev.springstore01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Needed Anotation, defined in the 1st import
public class HomeController {
  // Used for this method to be called when we send a request to the root of our website
  // Defined in the 2nd import
  @RequestMapping("/")
  public String index() {
    // We need to return the name of the view that should be returned to the browser
    return "index.html";
  }
}
