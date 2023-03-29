package kg.ojbgrsx.spring.controllers.FirstProject;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model) {
        model.addAttribute("message", name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        System.out.println(name + " " + surname);
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculate(@RequestParam("a") int a,
                            @RequestParam("b") int b,
                            @RequestParam("op") String operation,
                            Model model) {
        double result = 0;
        if (operation.equals("a")) {
            result = a + b;
        } else if (operation.equals("s")) {
            result = a - b;
        } else if (operation.equals("d") && b != 0) {
            result = (double)a / b;
        } else if (operation.equals("m")) {
            result = a * b;
        }
        model.addAttribute("result", result);
        return "first/calculator";
    }
}
