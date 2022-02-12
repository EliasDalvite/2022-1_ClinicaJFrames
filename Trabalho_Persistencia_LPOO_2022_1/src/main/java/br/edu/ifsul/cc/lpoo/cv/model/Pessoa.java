
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Pessoa implements Serializable{
 
    @Id
    private String cpf;
    
    @Column(nullable = false, length = 10)
    private String rg;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 20)
    private String senha;
    
    @Column(length = 11)
    private String numero_celular;
    
    @Column(length = 100)
    private String email;
    
    @Column(nullable = false, length = 100)
    private String cep;
    
    @Column(nullable = false, length = 100)
    private String endereco;
    
    @Column(length = 100)
    private String complemento;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_cadastro;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_nascimento;
    
    public Pessoa(){

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Calendar getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Calendar data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public Calendar getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Calendar data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
    
}


