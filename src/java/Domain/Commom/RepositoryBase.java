/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Commom;

import Domain.Repository.Hibernate.Configurations.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Leandro
 * @param <T>
 */
public abstract class RepositoryBase<T extends DomainBase> implements IRepositoryBase {

    public final  <T> T get(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        Class<T> tipo = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        
        
        session.close();
        return tipo.cast(session.get(tipo,id));
    };

    

    
   // public void update(T object);

   // public void delete(int id);

    //public List<T> getList();
}
