package kr.co.pcninc.bigdata.cooperationmodule.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="WORKS")
@Builder
public class Works {

    @EmbeddedId
    @GeneratedValue
    private WorksMultiId worksMultiId;

    @JoinColumn(name = "lock_active_user")
    @ManyToOne
    private User lockActiveUser;

    @Column(name = "file_path")
    private String filePath;

    @Column
    private String thput;

    @Column(name = "worker")
    private String worker;

    @Column(name = "worker_cmt")
    private String workerCmt;

    @Column(name = "crt_dt")
    private String crtDt;

    @Column(name = "mod_dt")
    private String modDt;

    @Column(name = "finish_yn")
    private char finishYn;

    @PrePersist
    public void prePersist() {
        this.finishYn = this.finishYn == '\u0000' ? 'N' : this.finishYn;
    }

}
