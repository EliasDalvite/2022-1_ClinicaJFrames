
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import org.junit.Test;


public class TestPersistenciaJPA {
    
    //@Test
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }


    @Test
    public void testGeracaoMedicoLogin() throws Exception {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");

            Medico m = persistencia.doLogin("Pedro", "1234");

            if(m == null){
                m = new Medico();
                m.setNome("Pedro");
                m.setSenha("1234");
                System.out.println("Cadastrou novo medico!");
            }else{
                System.out.println("Encontrou medico cadastrado!");
            }


            //persistencia.persist(m);
            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }
}