package com.fundamentos.springboot.fundamentos.service;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransactional(List<User> users) {
        users.stream()
                .peek(user -> LOG.info("Usuario Insertado" + user))
                .forEach(userRepository::save);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public void delete(Long id) {
        this.userRepository.delete(new User(id));
    }

    public User update(User newUser, Long id) {
        return this.userRepository.findById(id).map(
                user -> {
                    user.setEmail(newUser.getEmail());
                    user.setBirtDate(newUser.getBirtDate());
                    user.setName(newUser.getName());
                    return this.userRepository.save(user);
                }
        ).get();
    }
}
