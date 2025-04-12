package coworkingApp.service;


import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.SpaceType;
import coworkingApp.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Transactional
    public CoworkingSpace addCoworkingSpace(SpaceType type, double price) {
        CoworkingSpace space = new CoworkingSpace(price, type);
        return spaceRepository.save(space);
    }

    @Transactional
    public void removeSpace(int id) {
        if (!spaceRepository.existsById(id)) {
            throw new IllegalArgumentException("Space with ID " + id + " does not exist.");
        }
        spaceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CoworkingSpace> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CoworkingSpace> getAvailableSpaces() {
        return spaceRepository.findByIsAvailable(true); // Используем метод репозитория
    }

    @Transactional(readOnly = true)
    public Optional<CoworkingSpace> getSpaceById(int id) {
        return spaceRepository.findById(id);
    }
}
