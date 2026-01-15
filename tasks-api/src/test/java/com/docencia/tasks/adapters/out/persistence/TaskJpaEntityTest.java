package com.docencia.tasks.adapters.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class TaskJpaEntityTest {

    private static final String TITULO = "Soy un titulo";
    private static final long ID_INICIAL = 1l;
    private static final String DESCRIPCION = "Descripcion";
    TaskJpaEntity jpaEntity = null;

    @BeforeEach
    void setUp(){
        jpaEntity = new TaskJpaEntity();
    }

    @Test
    @Order(1)
    void jpaEntityNotNullTest(){
        Assertions.assertNotNull(jpaEntity, "El valor no puede ser nulo");
    }

    @Test
    @Order(2)
    void jpaEntityGetsSetsTest(){
        jpaEntity.setCompleted(true);
        jpaEntity.setDescription(DESCRIPCION);
        jpaEntity.setId(ID_INICIAL);
        jpaEntity.setTitle(TITULO);

        Assertions.assertTrue(jpaEntity.isCompleted());
        Assertions.assertEquals(DESCRIPCION, jpaEntity.getDescription());
        Assertions.assertEquals(ID_INICIAL, jpaEntity.getId());
        Assertions.assertEquals(TITULO, jpaEntity.getTitle());
    }
}
