
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Agenda;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PersistenciaJPA implements InterfacePersistencia{
    
    public EntityManagerFactory factory;    //fabrica de gerenciadores de entidades
    public EntityManager entity;            //gerenciador de entidades JPA
    
    public PersistenciaJPA(){
        
        //parametro: é o nome da unidade de persistencia (Persistence Unit)
        factory = Persistence.createEntityManagerFactory("clinica_lpoo_2021_2");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        
        return entity.isOpen();   
    }

    @Override
    public void fecharConexao() {
        
        entity.close();        
    }
    
    @Override
    public Object find(Class c, Object id) throws Exception {
        
        return entity.find(c, id);//encontra um determinado registro        
    }

    @Override
    public void persist(Object o) throws Exception {

        entity.getTransaction().begin();// abrir a transacao.
        entity.persist(o); //realiza o insert ou update.
        entity.getTransaction().commit(); //comita a transacao (comando sql)
        
    }

    @Override
    public void remover(Object o) throws Exception {

        entity.getTransaction().begin();// abrir a transacao.
        entity.remove(o); //realiza o delete
        entity.getTransaction().commit(); //comita a transacao (comando sql)

    }
    
    @Override
    public List<Agenda> listAgendas() {

        throw new UnsupportedOperationException("Não disponivel");
        
    }
    
    @Override
    public List<Medico> listMedicos() {

        throw new UnsupportedOperationException("Não disponivel");
        
    }

    @Override
    public Medico doLogin(String nome, String senha) throws Exception {

        List<Medico> list = entity.createNamedQuery("Medico.logib").setParameter("ParamN", nome).setParameter("ParamS", senha).getResultList();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }

    }
}
