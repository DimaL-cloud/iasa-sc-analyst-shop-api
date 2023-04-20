package iasa.sc.site.Backend.services.impl;

import iasa.sc.site.Backend.dtos.PrintDto;
import iasa.sc.site.Backend.dtos.mappers.PrintMapper;
import iasa.sc.site.Backend.entities.Print;
import iasa.sc.site.Backend.exceptions.UnknownIdException;
import iasa.sc.site.Backend.exceptions.ValidationException;
import iasa.sc.site.Backend.repositories.PrintRepository;
import iasa.sc.site.Backend.services.ImageService;
import iasa.sc.site.Backend.services.PrintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrintServiceImpl implements PrintService {
    private final PrintRepository printRepository;

    private final ImageService imageService;

    @Override
    public ResponseEntity<List<PrintDto>> getAllPrints() {
        List<PrintDto> responseBody = printRepository
                .findAll()
                .stream()
                .map(PrintMapper.INSTANCE::printToDto)
                .toList();
        return new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Void> deletePrintById(String id) {
        try {
            UUID uuid = printRepository.findById(Integer.parseInt(id)).orElseThrow(RuntimeException::new).getUuid();
            printRepository.deleteById(Integer.parseInt(id));
            imageService.deleteAllImagesByUUID(uuid);
        } catch (Exception e) {
            throw new UnknownIdException("Id doesn`t exist in the database");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> addNewPrint(PrintDto printDto, List<MultipartFile> images) {
        try {
            Print inputPrint = PrintMapper.INSTANCE.printDtoToPrint(printDto);
            inputPrint = printRepository.save(inputPrint);
            if (images != null) {
                imageService.saveAllImages(images, inputPrint.getUuid());
            }
        } catch (Exception e) {
            throw new ValidationException("Something wrong in object`s fields");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    @Override
    public ResponseEntity<Void> updatePrint(PrintDto printDto, List<MultipartFile> images) {
        try {
            Print print = PrintMapper.INSTANCE.printDtoToPrint(printDto);
            print = printRepository.save(print);
            if (images != null) {
                imageService.saveAllImages(images, print.getUuid());
            }
        } catch (Exception e) {
            throw new ValidationException();
        }
        return ResponseEntity.status(202).build();
    }

    @Override
    public ResponseEntity<PrintDto> getPrintById(String id) {
        try {
            return ResponseEntity.ok(PrintMapper.INSTANCE
                    .printToDto(printRepository
                            .findById(Integer.parseInt(id))
                            .orElseThrow(RuntimeException::new)));
        } catch (Exception e) {
            throw new UnknownIdException();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteAllPrints() {
        List<Print> prints = printRepository.findAll();
        prints.forEach(print -> imageService.deleteAllImagesByUUID(print.getUuid()));
        printRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
