package kr.co.pcninc.bigdata.cooperationmodule.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import kr.co.pcninc.bigdata.cooperationmodule.domain.WorksMultiId;
import kr.co.pcninc.bigdata.cooperationmodule.repository.WorksRepository;

@Service
public class WorkService {

    @Autowired
    WorksRepository worksRepository;

    public List<Works> findAll() {return worksRepository.findAll();}

    public List<Works> findByWsId(int wsId) {return worksRepository.findByWsId(wsId);}

    public Works findById(WorksMultiId worksMultiId) {return worksRepository.findById(worksMultiId).orElse(null);}

    public void save(Works work) {worksRepository.save(work);}

    public void deleteByWsId(int wsId) {worksRepository.deleteByWsId(wsId);}

}
