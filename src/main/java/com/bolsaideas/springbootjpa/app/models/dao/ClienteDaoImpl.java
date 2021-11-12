package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }


    @Transactional
    @Override
    public void save(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0) {  //si el id es diferente a null y mayor que cero actualicelo
            em.merge(cliente);
        } else {
            em.persist(cliente);  // de lo contrario cree un nuevo cliente en la BD
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        return em.find(Cliente.class, id);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        Cliente cliente = findOne(id);
        em.remove(cliente);
    }
}
