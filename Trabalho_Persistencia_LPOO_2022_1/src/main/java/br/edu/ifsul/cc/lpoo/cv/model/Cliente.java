
package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_cliente")
@DiscriminatorValue("C")
public class Cliente extends Pessoa{
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_ultima_visita;
    
    @OneToMany(mappedBy = "cliente")
    private List<Pet> pets;
    
    public Cliente(){
        
    }

    public Calendar getData_ultima_visita() {
        return data_ultima_visita;
    }

    public void setData_ultima_visita(Calendar data_ultima_visita) {
        this.data_ultima_visita = data_ultima_visita;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
