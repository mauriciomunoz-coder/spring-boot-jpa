package com.bolsaideas.springbootjpa.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import com.bolsaideas.springbootjpa.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    private IClienteService ClienteService;


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,  Model model) {

        Pageable pageRequest =  PageRequest.of(page, 4);

        Page<Cliente> clientes = ClienteService.findAll(pageRequest);  //recupera una lista paginada
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
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
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = null;

        if (id > 0) {
            cliente = ClienteService.findOne(id);
            if (cliente == null){
                flash.addFlashAttribute("error","El id del cliente No existe en la base de datos!");
                return "redirect:/listar";

            }
        } else {
            flash.addFlashAttribute("error","El id del cliente No puede ser cero");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "/form";
    }


    //guarda el cliente en la BD
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con Exito!" : "Cliente creado con Exito!";

        ClienteService.save(cliente);
        status.setComplete(); //elimina el objeto de la sesion
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            ClienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito");
        }
        return "redirect:/listar";
    }
}
