package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;

import java.util.List;

public interface IClienteDao {

    public List<Cliente> findAll();
}
