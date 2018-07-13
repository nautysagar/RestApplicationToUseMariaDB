package com.nashtech.controllers;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nashtech.dataCollector.models.User;
import com.nashtech.dataCollector.models.UserDao;
import com.nashtech.dataCollector.services.UserService;

/**
 * Class UserController
 */
@Controller
public class UserController {


	@Autowired
	 private UserService userService;
 

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="/create")
  public String create(
          @Min(value = 4, message = "error.user.emailLengthMin")
          @NotNull(message = "error.user.emailNotNull")
          @Email(message = "error.user.emailFormatIncorrect")
          String email, String name, Model model) {

    User user = userService.create(email, name);
    model.addAttribute("user", user);
    return "/test";
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }
    return "User succesfully deleted!";
  }
  
  /**
   * Retrieve the id for the user with the passed email address.
   */
  @RequestMapping(value="/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    try {
      User user = userDao.getByEmail(email);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found: " + ex.toString();
    }
    return "The user id is: " + userId;
  }
  
  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @RequestMapping(value="/update")
  @ResponseBody
  public String updateName(long id, String email, String name) {
    try {
      User user = userDao.getById(id);
      user.setEmail(email);
      user.setName(name);
      userDao.update(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  } 

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // Wire the UserDao used inside this controller.

  
  @Autowired
  private UserDao userDao;
  
} // class UserController
