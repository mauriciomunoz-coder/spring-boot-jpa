package com.bolsaideas.springbootjpa.app.models.service;


import com.bolsaideas.springbootjpa.app.models.dao.IClienteDao;
import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {


    @Autowired
    IClienteDao clienteDao;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }


    //metodo de encontrar todos los clientes pero para implementar el paginador
    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }
}
