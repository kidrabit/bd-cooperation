package kr.co.pcninc.bigdata.cooperationmodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.sun.istack.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="CODE")
@Builder
public class Code {

    @Id
    @Column(name = "code_id")
    //private int codeId;
    private String codeId;

    @Column(name = "code_name")
    @NotNull
    private String codeName;

}
