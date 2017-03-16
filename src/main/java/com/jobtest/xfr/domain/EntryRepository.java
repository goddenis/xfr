package com.jobtest.xfr.domain;


import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Singleton
public class EntryRepository {

    @Inject
    private EntityManagerFactory emf;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public void saveEntry( Entry entry) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Set<ConstraintViolation<Entry>> validate = factory.getValidator().validate(entry);
        if (validate.size()>0){
            throw new ValidationException(
                    validate
                            .stream()
                            .map(e->e.getMessage())
                            .reduce("Constraint violations:",(x,y)->x+'\n'+y));
        }
        transaction.begin();

        em.persist(entry);
//        em.flush();
        transaction.commit();
        em.close();
    }
}
