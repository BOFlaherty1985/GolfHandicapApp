package test.java.co.uk.myhandicap.controllers.myAccount;

import main.java.co.uk.myhandicap.model.user.User;
import main.java.co.uk.myhandicap.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Change Password Form Controller
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 29/07/2014
 * @project MyHandicapApp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/config/testContext.xml",
        "classpath:/main/webapp/WEB-INF/MyHandicapApp-servlet.xml"})
@WebAppConfiguration
public class ChangePasswordControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserService userService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private final String USER = "TEST_PRINCIPAL";

    Principal principal = new Principal() {
        @Override
        public String getName() {
            return USER;
        }};

    @Test
    public void changePasswordControllerDefaultRequestMapping() throws Exception {

        when(userService.findUserByUsername(USER)).thenReturn(new User());

        mockMvc.perform(get("/myAccount/changeAccountPassword").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("myAccount/changePassword"))
                .andExpect(forwardedUrl("/WEB-INF/views/myAccount/changePassword.jsp"))
                .andExpect(model().attributeExists("user"));

    }

    // test that the user object retrieved by the model has valid properties (i.e not null or empty)
    @Test
    public void changePasswordControllerUserObjectHasCorrectParameters() throws Exception {

        when(userService.findUserByUsername(USER)).thenReturn(buildMockUser(1L));

        mockMvc.perform(get("/myAccount/changeAccountPassword").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("myAccount/changePassword"))
                .andExpect(forwardedUrl("/WEB-INF/views/myAccount/changePassword.jsp"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("id", is(1L))))
                .andExpect(model().attribute("user", hasProperty("username", is("TEST_PRINCIPAL"))))
                .andExpect(model().attribute("user", hasProperty("firstName", is("Testing"))))
                .andExpect(model().attribute("user", hasProperty("lastName", is("Mctester"))));
    }

    // test posting data to the controller with valid parameters (password/confirmPassword)
    @Test
    public void changePasswordControllerPostValidFormData() throws Exception {

        when(userService.retrieveUserFromSecurityContext()).thenReturn(new User());

        mockMvc.perform(post("/myAccount/changeAccountPassword/update")
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("password", "abcdefgh")
                .param("confirmPassword", "abcdefgh")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("myAccount/changePassword"))
                .andExpect(forwardedUrl("/WEB-INF/views/myAccount/changePassword.jsp"));
    }

    // test posting data to the controller with invalid parameters (password/confirmPassword)
    @Test
    public void changePasswordControllerPostInvalidFormData() throws Exception {

        mockMvc.perform(post("/myAccount/changeAccountPassword/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", "abcdefgh")
                        .param("confirmPassword", "qwertyuy")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("myAccount/changePassword"))
                .andExpect(forwardedUrl("/WEB-INF/views/myAccount/changePassword.jsp"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attribute("status", "Please correct the error(s) and resubmit the form."));
    }

    // test that the correct number of error messages are displayed to the user (also from our custom validator)
    // password must be a minimum of 6 characters in length
    // password must not exceed 15 characters in length
    @Test
    public void changePasswordControllerPostInvalidPasswordMinLengthAndMismatchPasswordsAndFailValidation() throws Exception {

        mockMvc.perform(post("/myAccount/changeAccountPassword/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", "ac")
                        .param("confirmPassword", "qwertyuy")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("myAccount/changePassword"))
                .andExpect(forwardedUrl("/WEB-INF/views/myAccount/changePassword.jsp"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(2))
                .andExpect(model().attribute("status", "Please correct the error(s) and resubmit the form."));
    }

    // test password/confirmPassword validation behavior when an empty value is entered into either or both of the screen fields

    // test that a call to the userService has been made to save the updated data


    private User buildMockUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(USER);
        user.setFirstName("Testing");
        user.setLastName("Mctester");
        return user;
    }
}
