package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.MakerRequest;
import bogdan.kuzenko.shop.dto.response.MakerResponse;
import bogdan.kuzenko.shop.entity.Maker;
import bogdan.kuzenko.shop.repository.MakerRepository;
import bogdan.kuzenko.shop.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakerService {

    @Autowired
    private MakerRepository makerRepository;

    @Autowired
    private FileTool fileTool;

    @Value("${makers.img.directory}")
    private String imgDirectory;

    public void create(MakerRequest request) throws IOException {
        makerRepository.save(makerRequestToMaker(null, request));
    }

    public List<MakerResponse> findAll() {
        return makerRepository.findAll().stream().map(MakerResponse::new).collect(Collectors.toList());
    }

    public Maker findOne(Long id) {
        return makerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Maker with id " + id + " not exist"));
    }

    public void update(Long id, MakerRequest request) throws IOException {
        makerRepository.save(makerRequestToMaker(findOne(id), request));
    }

    public void delete(Long id) {
        makerRepository.delete(findOne(id));
    }


    private Maker makerRequestToMaker(Maker maker, MakerRequest request) throws IOException {
        if (maker == null) {
            maker = new Maker();
        }
        if (request.getLogoImage() != null) {
            maker.setLogoImage(fileTool.saveFile(request.getLogoImage(), imgDirectory));
        }
        maker.setName(request.getName());
        maker.setDescription(request.getDescription());
        return maker;
    }
}
