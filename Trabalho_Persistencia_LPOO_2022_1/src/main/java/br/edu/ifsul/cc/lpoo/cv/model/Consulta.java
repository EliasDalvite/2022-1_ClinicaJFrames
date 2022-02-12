
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_consulta")
public class Consulta implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_consulta", sequenceName = "seq_consulta_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_consulta", strategy = GenerationType.SEQUENCE)   
    private Integer id;
    
    @Column(precision = 2, nullable = false)
    private Float valor;
    
    @Column(nullable = true, length = 100)
    private String observacao;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_retorno;
    
    @ManyToOne
    @JoinColumn(name = "medico_crmv", nullable = false)
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    
    @OneToMany(mappedBy = "consulta")
    private List<Receita> receitas;
    
    public Consulta(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Calendar getData_retorno() {
        return data_retorno;
    }

    public void setData_retorno(Calendar data_retorno) {
        this.data_retorno = data_retorno;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }
    
    
}
