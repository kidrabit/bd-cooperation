package kr.co.pcninc.bigdata.cooperationmodule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuth;
import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuthMultiId;
import kr.co.pcninc.bigdata.cooperationmodule.repository.UserAuthRepository;

@Service
public class UserAuthService {

    @Autowired
    UserAuthRepository userAuthRepository;

    public List<UserAuth> findAll(){ return userAuthRepository.findAll(); }

    public void save(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }

    public List<String> findByUserId(String userId) { return userAuthRepository.getCodeId(userId); }

    public UserAuth findById(UserAuthMultiId id) { return userAuthRepository.findById(id).orElse(null); }

    public void delete(UserAuthMultiId id){
        userAuthRepository.deleteById(id);
    }

}
