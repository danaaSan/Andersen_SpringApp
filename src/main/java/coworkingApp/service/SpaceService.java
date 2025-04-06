package coworkingApp.service;


import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.SpaceType;
import coworkingApp.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Transactional
    public void addCoworkingSpace(SpaceType type, double price) {
        CoworkingSpace space = new CoworkingSpace(price, type);
        spaceRepository.save(space);
    }

    @Transactional
    public void removeSpace(int id) {
        spaceRepository.deleteById(id);
    }

    public void getAllSpaces() {
        spaceRepository.findAll().forEach(System.out::println);
    }

    public List<CoworkingSpace> getAvailableSpaces() {
        return spaceRepository.findByIsAvailable(true);
    }
}
