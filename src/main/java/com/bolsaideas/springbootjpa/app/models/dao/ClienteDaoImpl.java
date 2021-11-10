package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ClienteDaoImpl implements IClienteDao{

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }
}
