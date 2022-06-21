package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

    private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

    private ComponentDependency componentDependency;
    private MyBean myBean;
    private MyBeanWithDependency myBeanWithDependency;

    private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
    private UserRepository userRepository;

    private UserService userService;

    public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
                                  MyBean myBean,
                                  MyBeanWithDependency myBeanWithDependency,
                                  MyBeanWithProperties myBeanWithProperties,
                                  UserPojo userPojo,
                                  UserRepository userRepository,
                                  UserService userService
    ) {
        this.componentDependency = componentDependency;
        this.myBean = myBean;
        this.myBeanWithDependency = myBeanWithDependency;
        this.myBeanWithProperties = myBeanWithProperties;
        this.userPojo = userPojo;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FundamentosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //ejemplosAnteriores();
        //saveUsersInDataBase();
        //getInformationJpqlFromUser();

        saveWithErrorTransactional();

    }


    private void saveWithErrorTransactional() {
        User test1 = new User("t1", "t1@gmail.com", LocalDate.of(1998, 01, 03));
        User test2 = new User("t2", "t2@gmail.com", LocalDate.of(1999, 02, 04));
        User test3 = new User("t3", "t3@gmail.com", LocalDate.of(2000, 03, 05));
        User test4 = new User("t4", "t4@gmail.com", LocalDate.of(2001, 04, 06));

        List<User> userList = Arrays.asList(test1, test2, test3, test4);

        try {
            this.userService.saveTransactional(userList);
        } catch (Exception e) {
            LOGGER.error("Esta es una excepcion dentro del metodo transaccional : " + e);
        }
        this.userService.getAllUsers()
                .stream()
                .forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transactional = " + user));
    }


    private void getInformationJpqlFromUser() {
        LOGGER.info("Usuario con metodo find by email = " +
                this.userRepository.findByUserEmail("anak@domaincom")
                        .orElseThrow(() -> new RuntimeException("No se encontro el usuario"))
        );

        this.userRepository.findAndSort("Lu", Sort.by("id").descending())
                .stream()
                .forEach(user -> LOGGER.info("User with method sort : " + user));

        this.userRepository.findByName("John")
                .stream()
                .forEach(user -> LOGGER.info("Usuario con Query Method: " + user));

        LOGGER.info("Usuario con metodo find by Query methos by name and email = " +
                this.userRepository.findByNameAndEmail("ximon", "xi@domaincom")
                        .orElseThrow(() -> new RuntimeException("No se encontro el usuario"))
        );


        this.userRepository.findByNameLike("Lu%")
                .stream()
                .forEach(user -> LOGGER.info("Usuario QUery Method and Like " + user));

        this.userRepository.findByNameOrEmail("Luisa", "pl@domaincom")
                .stream()
                .forEach(user -> LOGGER.info("Usuario QUery Method and Or " + user));


        this.userRepository.findByBirtDateBetween(LocalDate.of(2017, 9, 10), LocalDate.of(2021, 4, 1))
                .stream()
                .forEach(user -> LOGGER.info("Usuario Query Method and Between BirtDay " + user));

        this.userRepository.findByNameLikeOrderByIdDesc("P%")
                .stream()
                .forEach(user -> LOGGER.info("Usuario Query Method findByNameLikeOrderByIdDesc " + user));

        LOGGER.info("Usuario con metodo getAllByBirtDateAndEmail named params = " +
                this.userRepository.getAllByBirtDateAndEmail(LocalDate.of(2020, 6, 22), "xi@domaincom")
                        .orElseThrow(() -> new RuntimeException("No se encontro el usuario"))
        );

    }

    private void saveUsersInDataBase() {
        User user1 = new User("John", "john@domaincom", LocalDate.of(2021, 3, 20));
        User user2 = new User("Luisa", "lu@domaincom", LocalDate.of(2018, 10, 18));
        User user3 = new User("ximon", "xi@domaincom", LocalDate.of(2020, 6, 22));
        User user4 = new User("Wolfverine", "wolf@domaincom", LocalDate.of(2022, 7, 23));
        User user5 = new User("Anakin", "anak@domaincom", LocalDate.of(2022, 6, 24));
        User user6 = new User("LuReno", "re@domaincom", LocalDate.of(2022, 9, 25));
        User user7 = new User("Platon", "pl@domaincom", LocalDate.of(2022, 11, 1));
        User user8 = new User("Perseo", "pe@domaincom", LocalDate.of(2021, 2, 2));
        User user9 = new User("AnaLisa", "ali@domaincom", LocalDate.of(2022, 12, 7));
        User user10 = new User("Paul", "paul@domaincom", LocalDate.of(2022, 8, 18));
        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
        list.stream().forEach(this.userRepository::save);

    }

    private void ejemplosAnteriores() {
        componentDependency.saludar();
        myBean.print();
        myBeanWithDependency.printWithDependency();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail() + " " + userPojo.getPassword());
        LOGGER.error("Esto es un error del aplicativo");
    }
}
