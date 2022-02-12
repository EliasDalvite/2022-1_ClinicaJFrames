
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="tb_receita")
public class Receita implements Serializable{
   
    @Id
    @SequenceGenerator(name = "seq_receita", sequenceName = "seq_receita_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_receita", strategy = GenerationType.SEQUENCE)   
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String orientacao;
    
    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;
    
    @ManyToMany
    @JoinTable(name = "tb_receita_produto", joinColumns = {@JoinColumn(name = "receita_id")}, 
                                       inverseJoinColumns = {@JoinColumn(name = "produto_id")})
    private List<Produto> produtos;
    
    public Receita(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
