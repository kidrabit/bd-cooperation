package kr.co.pcninc.bigdata.cooperationmodule.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Workspace;
import kr.co.pcninc.bigdata.cooperationmodule.repository.WorkspaceRepository;
@Service
@Transactional
public class WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    public List<Workspace> findAll(){ return workspaceRepository.findAll(); }

    public Page<Workspace> findAll(Pageable pageable){
        return workspaceRepository.findAll(pageable);
    }

    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    public Workspace findById(int id) { return workspaceRepository.findById(id).orElse(null); }

    public void delete(int id){
        workspaceRepository.deleteById(id);
    }

    public List<Workspace> findIncomplete() {  return workspaceRepository.findIncomplete(); }

}
