package com.bolsaideas.springbootjpa.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import com.bolsaideas.springbootjpa.app.models.service.IClienteService;
import com.bolsaideas.springbootjpa.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    private IClienteService ClienteService;

    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String,Object> model, RedirectAttributes flash){

        Cliente cliente = ClienteService.findOne(id);
        if (cliente == null){
            flash.addFlashAttribute("error", "El cliente No existe en la base de datos");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Detalle Cliente : " + cliente.getNombre());

        return "ver";
    }


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,  Model model) {

        Pageable pageRequest =  PageRequest.of(page, 4);

        Page<Cliente> clientes = ClienteService.findAll(pageRequest);  //recupera una lista paginada

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes); //objeto con la lista de clientes y la url a donde va dirigida
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender); //pasamos el objeto pageRender a la vista
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
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        // *****************  codigo que guarda la imagen o foto  *******************************
        if (!foto.isEmpty()){

            String rootPath = "C://Temp//uploads";

            try {
                byte[] bytes = foto.getBytes();
                Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename()); //da la ruta donde se debe guardar la foto
                Files.write(rutaCompleta, bytes);    //guarda la foto en la ruta especificada
                flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");

                cliente.setFoto(foto.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //   ******************  fin codigo foto *********************

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
