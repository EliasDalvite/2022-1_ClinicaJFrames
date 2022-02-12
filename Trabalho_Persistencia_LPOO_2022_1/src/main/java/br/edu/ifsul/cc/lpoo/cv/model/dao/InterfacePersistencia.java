
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Agenda;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import java.util.List;


public interface InterfacePersistencia {
    
    public Boolean conexaoAberta();
    
    public void fecharConexao();
    
    public Object find(Class c, Object id) throws Exception;//select.
    
    public void persist(Object o) throws Exception;//insert ou update.
    
    public void remover(Object o) throws Exception;//delete.
    
    public List<Agenda> listAgendas() throws Exception;
    
    public List<Medico> listMedicos() throws Exception;
}
