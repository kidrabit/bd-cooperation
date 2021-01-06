package kr.co.pcninc.bigdata.cooperationmodule.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class UserAuthMultiId  implements Serializable {
    private static final long serialVersionUID = 1451234123412431231L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "code_id")
    //private int codeId;
    private String codeId;
}
