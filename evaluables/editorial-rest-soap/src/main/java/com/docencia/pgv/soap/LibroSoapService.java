package com.docencia.pgv.soap;

import java.util.List;
import com.docencia.pgv.modelo.Libro;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ies.docencia.com/libro", name = "LibroPortType")
public interface LibroSoapService {
    /**
     * Lista todos los libros
     * 
     * @return List con los libros
     */
    @WebMethod
    List<Libro> getAllBooks();

    /**
     * Busca un libro por id
     * 
     * @param id id del libro
     * @return Libro buscado
     */
    @WebMethod
    Libro getBookById(@WebParam(name = "id") Long id);

    /**
     * Busca los libros de un autor
     * 
     * @param idAutor id del autor
     * @return Lista con los libros del autor
     */
    @WebMethod
    List<Libro> getBooksByAuthor(@WebParam(name = "idAutor") Long idAutor);

    /**
     * Crea un libro
     * 
     * @param titulo          titulo del libro
     * @param anioPublicacion anio de publicacion del libro
     * @param idAutor         id del autor del libro
     * @return el libro creado
     */
    @WebMethod
    Libro createBook(@WebParam(name = "titulo") String titulo,
            @WebParam(name = "anioPublicacion") Integer anioPublicacion,
            @WebParam(name = "idAutor") Long idAutor);

    /**
     * Borra un libro por id
     * 
     * @param id id del libro
     */
    @WebMethod
    void deleteBook(@WebParam(name = "id") Long id);
}
