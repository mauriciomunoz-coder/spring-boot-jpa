package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IClienteDao {

    public List<Cliente> findAll();
}
