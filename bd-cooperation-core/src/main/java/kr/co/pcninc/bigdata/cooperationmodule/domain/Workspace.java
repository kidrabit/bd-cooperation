package kr.co.pcninc.bigdata.cooperationmodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

import com.sun.istack.NotNull;
@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="WORKSPACE")
@Builder
public class Workspace {

    @Id
    @Column(name = "ws_id")
    @GeneratedValue
    private int wsId;

    @Column(name = "ws_name")
    private String wsName;

    @Column(name="ws_ds")
    private String wsDs;

    @Column(name="last_work")
    private String lastWork;

    @Column(name="last_work_comt")
    private String lastWorkComt;

    @Column(name="ws_crt_dt")
    @NotNull
    private String wsCrtDt;

    @Column(name="ws_mod_dt")
    private String wsModDt;

    @Column(name="percent")
    private String percent;

    @Column(name="user_lock_yn")
    private char userLockYn;

    @Column(name = "log_file_path")
    private String logFilePath;

    @ManyToOne
    @JoinColumn(name="last_worker")
    private User lastWorker;


    @PrePersist
    public void prePersist(){
        this.percent = this.percent == null? "0" : this.percent;
        this.userLockYn = this.userLockYn == '\u0000' ? 'N' : this.userLockYn;
    }


}
