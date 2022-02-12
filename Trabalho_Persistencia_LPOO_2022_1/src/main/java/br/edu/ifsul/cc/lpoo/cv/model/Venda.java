
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="tb_venda")
public class Venda implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_venda", sequenceName = "seq_venda_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_venda", strategy = GenerationType.SEQUENCE)   
    private Integer id;
    
    @Column(nullable = true, length = 100)
    private String observacao;
    
    @Column(precision = 2, nullable = false)
    private Float valor_total;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data;
    
    @ManyToOne
    @JoinColumn(name = "cliente_ultima_visita", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "funcionario_ctps", nullable = false)
    private Funcionario funcionario;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Pagamento pagamento;
    
    @ManyToMany
    @JoinTable(name = "tb_venda_consulta", joinColumns = {@JoinColumn(name = "venda_id")}, 
                                       inverseJoinColumns = {@JoinColumn(name = "consulta_id")})
    private List<Consulta> consultas;
    
    @ManyToMany
    @JoinTable(name = "tb_venda_produto", joinColumns = {@JoinColumn(name = "venda_id")}, 
                                       inverseJoinColumns = {@JoinColumn(name = "produto_id")})
    private List<Produto> produtos;
    
    public Venda(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Float getValor_total() {
        return valor_total;
    }

    public void setValor_total(Float valor_total) {
        this.valor_total = valor_total;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
}
