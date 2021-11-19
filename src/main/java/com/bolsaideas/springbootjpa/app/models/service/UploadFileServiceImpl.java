package com.bolsaideas.springbootjpa.app.models.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {


    @Override
    public Resource load(String filename) throws MalformedURLException {  //carga la foto para que pueda ser vista

        Path pathFoto = getPath(filename);
        Resource recurso = null;


        recurso = new UrlResource(pathFoto.toUri());
        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("Error No se puede cargar la imagen: " + pathFoto.toString());

        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {   //guarda la foto en la ruta especificada

        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); //le da un nombre unico a la imagen
        Path rootPath = getPath(uniqueFilename);


        Files.copy(file.getInputStream(), rootPath);
        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {  //elimina la foto

        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if (archivo.exists() && archivo.canRead()) {
            if (archivo.delete()) {
                return true;
            }
        }

        return false;
    }


    public Path getPath(String filename) {
        return Paths.get("uploads").resolve(filename).toAbsolutePath();

    }
}
