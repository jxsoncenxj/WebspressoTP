package com.group15.Webspresso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.ui.ExtendedModelMap;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.group15.Webspresso.controller.UserController;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.UserRepository;
import com.group15.Webspresso.service.UserService;
import com.group15.Webspresso.service.impl.UserServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class WebspressoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService = new UserServiceImpl(userRepository);

    private final UserController userController = new UserController(userService);

    @Test
    public void testCreateUserForm(){
        UserController controller = new UserController(userService);
        Model model = new BindingAwareModelMap();

        String viewName  = controller.createUserForm(model);

        assertEquals("create_user", viewName);
        assertEquals(new User(), model.getAttribute("user"));
    }

    @Test
    public void testSignUpUserForm() {
        Model model = new BindingAwareModelMap();
        String viewName = userController.signUpUserForm(model);
    
        assertEquals("sign-up", viewName);
    
        User user = (User) model.getAttribute("user");
        assertNotNull(user);
        assertNull(user.getId());
    }

    @Test
    public void testUpdateUser(){
        //Arrange
        //Create a test user
        User user = new User();
        user.setId(1);
        user.setEmail("test@testcode.com");
        user.setPassword("testpassword");
        
        //Act
        //Call the controller method
        String result = userController.updateUser(user.getId(), user, new ExtendedModelMap());

        //Assert
        //Verify that the mehtod returns the expected values
        assertEquals("redirect:/users", result);

        //Verify that the was updated in UserService
        User updateUser = userService.getUserById(1);
        assertEquals(user.getEmail(), updateUser.getEmail());
        assertEquals(user.getPassword(), updateUser.getPassword());
    }

    @Test
    public void testSaveUser(){
        //Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("example@test");
        user.setPassword("testpassword");

        //Act
        String result = userController.saveUser(user);

        //Assert
        assertEquals("redirect:/users", result);

        //Verify that the user has been saved in UserService
        User savedUser = userService.getUserById(1);
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void testEditUserForm(){
        //Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("john@exmaple.com");

        UserService userService = Mockito.mock(UserService.class);
        when(userService.getUserById(1)).thenReturn(user);

        //Create a Hashmap for model objects
        Model model = new ExtendedModelMap();

        //Act
        String viewName = userController.editUserForm(1, model);

        //Assert
        assertEquals("edit_user", viewName);
        assertEquals(user, model.getAttribute("user"));
    }
    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("john@example.com");

        //Create a UserService Object 
        UserService userService = Mockito.mock(UserService.class);
        when(userService.getUserById(1)).thenReturn(user);

        // Act
        String viewName = userController.deleteUser(1);

        // Assert
        verify(userService, times(1)).deleteUserById(1);
        assertEquals("redirect:/users", viewName);
    }

}




	


