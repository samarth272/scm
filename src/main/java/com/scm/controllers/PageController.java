package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class PageController {

    @Autowired
    private UserService userService;
    
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page handler");
        
        //sending data to view
        model.addAttribute("name","Substring Technologies");
        model.addAttribute("youtube","Learn Code With Me");
        model.addAttribute("github","https://github.com/samarth272/scm20");
        
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", false);
        System.out.println("About page handler");
        
        return "about";
    }

    @RequestMapping("/services")
    public String aboutServices(){
        System.out.println("Service page handler");
        
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm=new UserForm();
        //default data
        // userForm.setName("Durgesh");
        // userForm.setAbout(("This is about : Write something about yourself"));
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        System.out.println("Processing registration");

        //fetch form data
        //userform
        System.out.println(userForm);

        //validate form data
        if(rBindingResult.hasErrors()){
            return "register";
        }

        //save to database

        //userservice

        //UserForm---> User
    //    User user= User.builder().name(userForm.getName()).email(userForm.getEmail()).password(userForm.getPassword()).about(userForm.getAbout()).phoneNumber(userForm.getPhoneNumber()).profilePic("https://www.pngkit.com/png/detail/126-1262807_instagram-default-profile-picture-png.png")
    //    .build();

       User user=new User();

       user.setName(userForm.getName());
       user.setEmail(userForm.getEmail());
       user.setPassword(userForm.getPassword());
       user.setAbout(userForm.getAbout());
       user.setPhoneNumber(userForm.getPhoneNumber());
      // user.setEnabled(true);
       user.setProfilePic("https://www.pngkit.com/png/detail/126-1262807_instagram-default-profile-picture-png.png");

       User savedUser=userService.saveUser(user);

       System.out.println("user saved :");
        //message="Registration Successful"

        //add the message:

        Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message",message);

        //redirect to login page
        return "redirect:/register";
    }
    



}
