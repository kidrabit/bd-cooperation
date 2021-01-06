package kr.co.pcninc.bigdata.cooperationmodule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Code;
import kr.co.pcninc.bigdata.cooperationmodule.repository.CodeRepository;

@Service
public class CodeService {
    @Autowired
    CodeRepository codeRepository;

    public List<Code> findAll(){ return codeRepository.findAll(); }

    public void save(Code code) {
        codeRepository.save(code);
    }

    public Code findById(String id) { return codeRepository.findById(id).orElse(null); }

    public void delete(String id){
        codeRepository.deleteById(id);
    }
}
