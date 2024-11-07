package tech.maou.patitasalrescate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.maou.patitasalrescate.exception.InvalidDogUUID;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogRegisterDTO;
import tech.maou.patitasalrescate.service.dog.DogService;

import java.util.UUID;

@RestController
@RequestMapping("api/dog")
public class DogController {
    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("age") int age,
            @RequestParam("sex") String sex,
            @RequestParam("vaccinated") boolean vaccinated,
            @RequestParam("sterilized") boolean sterilized,
            @RequestParam("images") MultipartFile[] images
            ) {


        this.dogService.save(DogRegisterDTO.builder()
                .title(title)
                .description(description)
                .age(age)
                .sex(sex)
                .vaccinated(vaccinated)
                .vaccinated(sterilized)
                .images(images)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<DogDTO>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int elements) {
        return ResponseEntity.ok().body(this.dogService.findAll(page, elements));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<DogDTO> findById(@PathVariable String id) {
        DogDTO dotDTO = this.dogService.findById(id);
        return ResponseEntity.ok().body(dotDTO);
    }
}