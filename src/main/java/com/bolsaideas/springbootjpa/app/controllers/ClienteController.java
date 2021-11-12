package com.bolsaideas.springbootjpa.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import com.bolsaideas.springbootjpa.app.models.dao.IClienteDao;
import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import com.bolsaideas.springbootjpa.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;



@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    private IClienteService ClienteService;


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", ClienteService.findAll());
        return "listar";
    }

    //redirige al formulario de crear cliente
    @GetMapping(value = "/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("titulo", "Formulario de Cliente");
        model.put("cliente", cliente);
        return "/form";
    }


    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Cliente cliente = null;

        if (id > 0) {
            cliente = ClienteService.findOne(id);
        } else {
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "/form";
    }


    //guarda el cliente en la BD
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }
        ClienteService.save(cliente);
        status.setComplete(); //elimina el objeto de la sesion
        return "redirect:/listar";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            ClienteService.delete(id);
        }
        return "redirect:/listar";
    }
}
