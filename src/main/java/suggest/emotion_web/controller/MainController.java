package suggest.emotion_web.controller;

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
