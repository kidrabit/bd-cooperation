package kr.co.pcninc.bigdata.cooperationmodule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuth;
import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuthMultiId;

public interface UserAuthRepository extends JpaRepository<UserAuth, UserAuthMultiId> {

    @Query(value = "SELECT CODE_ID FROM USER_AUTH WHERE USER_ID=?1", nativeQuery = true)
    List<String> getCodeId(String userId);

}
