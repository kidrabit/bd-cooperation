package kr.co.pcninc.bigdata.cooperationmodule.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.pcninc.bigdata.cooperationmodule.domain.User;
import kr.co.pcninc.bigdata.cooperationmodule.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){ return userRepository.findAll(); }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(String id) { return userRepository.findById(id).orElse(null); }

    public Optional<User> findByIdPw(String id) { return userRepository.findById(id); }

    public void delete(String id){
        userRepository.deleteById(id);
    }
}
