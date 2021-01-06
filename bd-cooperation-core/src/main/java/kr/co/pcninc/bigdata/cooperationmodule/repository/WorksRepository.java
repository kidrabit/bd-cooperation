package kr.co.pcninc.bigdata.cooperationmodule.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import kr.co.pcninc.bigdata.cooperationmodule.domain.WorksMultiId;


@Repository
public interface WorksRepository extends JpaRepository<Works, WorksMultiId> {

    @Query(value = "SELECT * FROM WORKS WHERE WS_ID=?1 ORDER BY WORK_ID ASC ", nativeQuery = true)
    List<Works> findByWsId(int wsId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM WORKS WHERE WS_ID=?1", nativeQuery = true)
    void deleteByWsId(int wsId);


}
