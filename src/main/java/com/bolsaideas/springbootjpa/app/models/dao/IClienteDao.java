package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface IClienteDao {

    public List<Cliente> findAll();

    public void save(Cliente cliente);
}
