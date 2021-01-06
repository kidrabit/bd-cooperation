package kr.co.pcninc.bigdata.cooperationmodule.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorksMultiId implements Serializable {
    private static final long serialVersionUID = 1451234123412431231L;

    @Column(name = "work_id")
    private int workId;


    @Column(name="ws_id")
    private int wsId;
}
