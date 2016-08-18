package ru.javarush.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javarush.task.model.User;
import ru.javarush.task.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String listUsers(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.getAllUsers());

        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user){
        if(user.getId() == 0){
            userService.add(user);
        }else {
            userService.edit(user);
        }

        return "redirect:/users";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
        userService.delete(id);

        return "redirect:/users";
    }

    @RequestMapping("edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("listUsers", userService.getAllUsers());

        return "users";
    }
//    @RequestMapping(value = "search{name}", method = RequestMethod.GET)
//    public String searchUser(@PathVariable("name") String name, Model model){
//        model.addAttribute("user", new User());
//        model.addAttribute("searchUsers", userService.search(name));
//
//        return "search";
//    }
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(name = "name") String name) {
        ModelAndView mv = new ModelAndView("listUsers");
        mv.addObject("user", userService.search(name));
        return mv;
    }

}
