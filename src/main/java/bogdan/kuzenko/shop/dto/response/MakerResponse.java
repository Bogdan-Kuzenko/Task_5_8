package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Maker;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakerResponse {

    private Long id;
    private String name;
    private String description;
    private String logoImage;


    public MakerResponse(Maker maker) {
        id = maker.getId();
        name = maker.getName();
        description = maker.getDescription();
        logoImage = maker.getLogoImage();
    }
}
