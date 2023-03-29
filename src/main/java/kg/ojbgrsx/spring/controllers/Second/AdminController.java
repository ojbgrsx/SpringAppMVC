package kg.ojbgrsx.spring.controllers.SecondProject;

import jakarta.persistence.Persistence;
import kg.ojbgrsx.spring.dao.PersonDAO;
import kg.ojbgrsx.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person){
        model.addAttribute("peopleList",personDAO.index());
        return "admin/adminPage";
    }

    @PatchMapping("/add")
    public String grantAdmin(@ModelAttribute("person")Person person){
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
