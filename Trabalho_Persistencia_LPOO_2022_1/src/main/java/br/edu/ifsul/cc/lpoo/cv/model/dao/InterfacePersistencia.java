
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Agenda;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import java.util.List;


public interface InterfacePersistencia {
    
    Boolean conexaoAberta();
    
    void fecharConexao();
    
    Object find(Class c, Object id) throws Exception;//select.
    
    void persist(Object o) throws Exception;//insert ou update.
    
    void remover(Object o) throws Exception;//delete.
    
    List<Agenda> listAgendas() throws Exception;
    
    List<Medico> listMedicos() throws Exception;

    List<Funcionario> listFuncionarios() throws Exception;

    Pessoa doLogin(String cpf, String senha) throws Exception;

}
