
package br.edu.ifsul.cc.lpoo.cv.test;


import br.edu.ifsul.cc.lpoo.cv.model.Agenda;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import static br.edu.ifsul.cc.lpoo.cv.model.TipoProduto.CONSULTA;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;


public class TestePersistenciaJDBC {
        
    @Test
    public void testConexao() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
        
    }
    
    //@Test
    public void testListPersistenciaAgenda() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Agenda> lista = persistencia.listAgendas();
            
            if(!lista.isEmpty()){
            
                for(Agenda a : lista){

                    System.out.println("ID: "+a.getId()+" Data inicial: "+a.getData_inicio()+" Data final: "+a.getData_fim()+" Observacao: "+a.getObservacao()+" Tipo_produto: "+a.getTipoproduto()+" Funcionario_ctps: "+a.getFuncionario().getNumero_ctps()+" Medico_crmv: "+a.getMedico().getNumero_crmv());
                    
                    persistencia.remover(a);
                }
            }else{
                
                System.out.println("Não encontrou a data de inicio");
                
                Medico med = new Medico();
                Funcionario func = new Funcionario();
                Agenda ag = new Agenda();
                
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Calendar data_fim = Calendar.getInstance();
                Calendar data_inicio = Calendar.getInstance();
                
                data_inicio.setTime(formato.parse("25/03/2021"));
                data_fim.setTime(formato.parse("25/08/2021"));
                ag.setData_inicio(data_inicio);
                ag.setData_fim(data_fim);
                
                func.setCpf("12345678911");
                ag.setFuncionario(func);
                med.setCpf("98765432111");
                ag.setMedico(med);
                ag.setObservacao("nenhuma");
                ag.setTipoproduto(CONSULTA);
                
                System.out.println("Não encontrou a data de inicio");
                persistencia.persist(ag); //insert na tabela. 
                
                System.out.println("Cadastrou a Agenda "+ag.getData_inicio());
            }
            
            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
    
    //@Test
    public void testListPersistenciaMedico() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Medico> lista = persistencia.listMedicos();
            
            if(!lista.isEmpty()){
            
                for(Medico med : lista){
                    
                    System.out.println("Cpf: "+med.getCpf()+" Rg: "+med.getRg()+" Nome: "+med.getNome()+" Senha: "+med.getSenha()+" Numero_celular: "+med.getNumero_celular()
                    +" Email: "+med.getEmail()+" Data_cadastro: "+med.getData_cadastro()+" Data_nascimento: "+med.getData_nascimento()+" Cep: "+med.getCep()+" Endereco: "+med.getEndereco()
                    +" Complemento: "+med.getComplemento()+" Numero_crmv: "+med.getNumero_crmv());
                                        
                    persistencia.remover(med);
                }

            }else{
                
                System.out.println("Não encontrou o Medico");
                
                Medico med = new Medico();
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Calendar data_nascimento = Calendar.getInstance();
                
                med.setCpf("98765432111");
                med.setNumero_crmv("123456789");
                med.setCep("99270000");
                med.setComplemento("Apartamento");
                med.setEmail("carlosandre@gmail.com");
                med.setEndereco("Rua General Osório");
                med.setNome("Carlos André");
                med.setNumero_celular("998452131");
                med.setRg("5446891354");
                med.setSenha("ca54862");
                data_nascimento.setTime(formato.parse("19/08/1998"));
                med.setData_nascimento(data_nascimento);
                
                persistencia.persist(med);
                System.out.println("Cadastrou o Medico "+med.getNumero_crmv());
            }
            
            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
    
    //@Test
    public void remover() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Medico> lista = persistencia.listMedicos();
            
            if(!lista.isEmpty()){
            
                for(Medico med : lista){                    
                    persistencia.remover(med);
                }   
            }  
        }        
    }
}