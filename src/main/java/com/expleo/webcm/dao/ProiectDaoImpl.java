package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ProiectDaoImpl implements ProiectDao{

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Override
    public List<Proiect> findUserIdByEmail(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Proiect where proiectId in \n" +
                "(Select idProiect from UserProiect WHERE idUser = \n" +
                "(Select id from UserExpleo WHERE email = :email))");
        query.setParameter("email", username);
        List<Proiect> proiecte = query.list();

        session.getTransaction().commit();

        return proiecte;
    }

    @Override
    public void saveNewProject(Proiect proiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(proiect);

        session.getTransaction().commit();
        session.close();
    }
}
