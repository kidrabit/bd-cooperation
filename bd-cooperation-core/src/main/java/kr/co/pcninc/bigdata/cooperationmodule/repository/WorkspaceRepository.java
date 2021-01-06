package kr.co.pcninc.bigdata.cooperationmodule.repository;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Workspace;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
    @Query(value = "SELECT * FROM workspace WHERE percent!='100'", nativeQuery = true)
    List<Workspace> findIncomplete();
}
