package iasa.sc.site.Backend.controller;

import iasa.sc.site.Backend.dto.PhotocardDTO;
import iasa.sc.site.Backend.service.PhotocardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photocards")
@RequiredArgsConstructor
public class PhotocardController {

    private final PhotocardService photocardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PhotocardDTO>> getAllPhotocards() {
        return photocardService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhotocardDTO> getItemById(@PathVariable int id) {
        return photocardService.get(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addItem(@RequestPart("photocardDTO") PhotocardDTO photocardDTO,
                                        @RequestPart("image") MultipartFile image) {
        return photocardService.add(photocardDTO, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> editItem(@PathVariable int id,
                                         @RequestPart("photocardDTO") PhotocardDTO photocardDTO) {
        return photocardService.editById(id, photocardDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAllItems() {
        return photocardService.deleteAllItems();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItemById(@PathVariable int id) {
        return photocardService.deleteById(id);
    }
}
