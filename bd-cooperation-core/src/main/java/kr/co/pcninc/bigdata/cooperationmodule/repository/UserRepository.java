package kr.co.pcninc.bigdata.cooperationmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.pcninc.bigdata.cooperationmodule.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
