/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import orm.Personas;

/**
 *
 * @author ander
 */
@Stateless
public class PersonasDao {
    
    //Objeto que contiene la conexión:
    @PersistenceContext
    EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<Personas> selectPersonas(){
        Query q = em.createQuery("Select p From Personas p ORDER BY p.ape ASC");
        return q.getResultList();
    }
    
     public List<Personas> selectPersonas(String texto){
        Query q = em.createQuery("Select p From Personas p Where p.ape LIKE :texto OR p.nom LIKE :texto ORDER BY p.ape ASC");
        q.setParameter("texto","%" + texto + "%");
        return q.getResultList();
    }
    
    public void insertPersona(Personas p){
        em.persist(p);
    }
    
    public void updatePersona(Personas p){
        em.merge(p);
    }
    
    public void deletePersona(Personas p){
        em.remove(em.merge(p));
    }
}
