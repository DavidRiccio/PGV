package com.docencia.pgv.soap;

import java.util.List;
import com.docencia.pgv.modelo.Autor;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ies.docencia.com/autor", name = "AutorPortType")
public interface AutorSoapService {
    /**
     * Lista todos los autores
     * 
     * @return Lista de autores
     */
    @WebMethod
    List<Autor> getAllAuthors();

    /**
     * Busca un autor por id
     * 
     * @param id id del autor
     * @return el autor buscado
     */
    @WebMethod
    Autor getAuthorById(@WebParam(name = "id") Long id);

    /**
     * Crea un autor
     * 
     * @param nombre nombre del autor
     * @param pais   pais del autor
     * @return autor creado
     */
    @WebMethod
    Autor createAuthor(@WebParam(name = "nombre") String nombre, @WebParam(name = "pais") String pais);

    /**
     * Borra un autor por id
     * 
     * @param id id del autor a borrar
     */
    @WebMethod
    void deleteAuthor(@WebParam(name = "id") Long id);
}
