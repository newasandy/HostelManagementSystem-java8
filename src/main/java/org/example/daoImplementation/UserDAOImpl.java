package org.example.daoImplementation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.example.daoInterface.UserDAO;
import org.example.model.Users;
import org.example.utils.EntityManages;

import java.security.spec.ECField;
import java.util.List;

public class UserDAOImpl extends BaseDAOImp<Users, Long> implements UserDAO {
    public UserDAOImpl(){
        super(Users.class);
    }
    EntityManages entityManages = new EntityManages();
    private  EntityManager entityManager = entityManages.getEntityManager();



    @Override
    public Users findByEmail(String email){
        try{
            return entityManager.createQuery("SELECT e FROM Users e WHERE e.email = :email", Users.class)
                    .setParameter("email",email)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Users> getUnallocatedUsers(){
        try{
            return entityManager.createQuery("SELECT u FROM Users u WHERE u.roles = :roles AND u.id NOT IN (SELECT ra.studentId.id FROM RoomAllocation ra WHERE ra.unallocationDate IS NULL)", Users.class)
                    .setParameter("roles", "USER")
                    .getResultList();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Users> getOnlyStudent(){
        try{
            return entityManager.createQuery("SELECT u FROM Users u WHERE u.roles = :roles",Users.class)
                    .setParameter("roles", "USER")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
