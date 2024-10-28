package suggest.taste_the_weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

  @GetMapping("/main")
  public String mainPage () {
    return "/main.html";
  }

  @GetMapping("/login")
  public String loginPage ()  {
    return "/account/login.html";
  }


}
