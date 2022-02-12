
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
        
        
@Entity
@Table(name="tb_agenda")        
public class Agenda implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_agenda", sequenceName = "seq_agenda_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_agenda", strategy = GenerationType.SEQUENCE)   
    private Integer id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_inicio;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_fim;
    
    @Column(nullable = true, length = 100)
    private String observacao;    
    
    @ManyToOne
    @JoinColumn(name = "funcionario_cpf", nullable = false)
    private Funcionario funcionario;
    
    @ManyToOne
    @JoinColumn(name = "medico_cpf", nullable = false)
    private Medico medico;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoproduto;
    
    public Agenda(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Calendar data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Calendar getData_fim() {
        return data_fim;
    }

    public void setData_fim(Calendar data_fim) {
        this.data_fim = data_fim;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public TipoProduto getTipoproduto() {
        return tipoproduto;
    }

    public void setTipoproduto(TipoProduto tipoproduto) {
        this.tipoproduto = tipoproduto;
    }
    
}
