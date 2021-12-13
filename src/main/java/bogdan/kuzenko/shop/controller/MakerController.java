package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.MakerRequest;
import bogdan.kuzenko.shop.dto.response.MakerResponse;
import bogdan.kuzenko.shop.service.MakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.MAKER_URL;

@CrossOrigin
@RestController
@RequestMapping(MAKER_URL)
public class MakerController {

    @Autowired
    private MakerService makerService;

    @PostMapping
    public void create(@Valid @RequestBody MakerRequest request) throws IOException {
        makerService.create(request);
    }

    @GetMapping
    public List<MakerResponse> findAll() {
        return makerService.findAll();
    }

    @PutMapping
    public void update(Long id, @Valid @RequestBody MakerRequest request) throws IOException {
        makerService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        makerService.delete(id);
    }
}
