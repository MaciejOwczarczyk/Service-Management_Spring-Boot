package pl.maciejowczarczyk.servicemanagement.user;

import lombok.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.maciejowczarczyk.servicemanagement.role.Role;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @TestConfiguration
    static class UserServiceImplIntegrationTest {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

//    @Autowired
//    private TestEntityManager entityManager;
//
//
//    @Autowired
//    UserRepository userRepository;

    @Before
    public void setUp() {
       User maciej = new User();
       maciej.setFirstName("Maciej");
       Mockito.when(userRepository.findByUsername(maciej.getFirstName())).thenReturn(maciej);
    }

    @Test
    public void findByUsername() {
//        User user = new User();
//        user.setEnabled(true);
//        user.setFirstName("maciej");
//        user.setLastName("maciej");
//        user.setUsername("maciek122@vp.pl");
//        user.setPassword("sasa");
//        entityManager.persist(user);
//        entityManager.flush();
//
//        User found = userRepository.findByUsername(user.getUsername());
//        assertThat(user.getUsername()).isEqualTo(found.getUsername());

        String name = "Maciej";
        User found = userRepository.findByUsername(name);
        assertThat(found.getFirstName()).isEqualTo(name);


    }

    @Test
    public void findAllByRoles() {
        List<User> userList = new ArrayList<>();
        Role role = new Role();
        role.setName("testRole");

        User user = new User();
        user.setEnabled(true);
        user.setFirstName("maciej");
        user.setLastName("maciej");
        user.setUsername("maciek122@vp.pl");
        user.setPassword("sasa");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        userRepository.save(user);

        User user1 = new User();
        user1.setEnabled(true);
        user1.setFirstName("maciej");
        user1.setLastName("maciej");
        user1.setUsername("maciek123@vp.pl");
        user1.setPassword("sasa");
        user1.setRoles(new HashSet<>(Collections.singletonList(role)));
        userRepository.save(user1);

        userList.add(user);
        userList.add(user1);

        List<User> found = userRepository.findAllByRoles(role);

        assertThat(userList.size()).isEqualTo(found.size());
    }

    @Test
    public void findAllById() {
    }

}