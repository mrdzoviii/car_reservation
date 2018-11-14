package ba.telegroup.car_reservation.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomRepositoryImpl {

    @PersistenceContext
    protected EntityManager entityManager;
}
