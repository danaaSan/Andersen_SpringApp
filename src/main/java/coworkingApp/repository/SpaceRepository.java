package coworkingApp.repository;

import coworkingApp.entity.Booking;
import coworkingApp.entity.CoworkingSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpaceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(CoworkingSpace space) {
        entityManager.persist(space);
    }

    public void deleteById(int id) {
        CoworkingSpace space = entityManager.find(CoworkingSpace.class, id);
        if (space != null) {
            entityManager.remove(space);
        }
    }

    public List<CoworkingSpace> findAll() {
        return entityManager.createQuery("FROM CoworkingSpace", CoworkingSpace.class).getResultList();
    }

    public List<CoworkingSpace> findByIsAvailable(boolean available) {
        return entityManager.createQuery("FROM CoworkingSpace WHERE isAvailable = :available", CoworkingSpace.class)
                .setParameter("available", available)
                .getResultList();
    }

    public Optional<CoworkingSpace> findById(int id) {
        CoworkingSpace space = entityManager.find(CoworkingSpace.class, id);
        return Optional.ofNullable(space);
    }

}
