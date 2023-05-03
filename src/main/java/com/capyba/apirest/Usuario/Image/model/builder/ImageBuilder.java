package com.capyba.apirest.Usuario.Image.model.builder;

import org.springframework.stereotype.Component;
import com.capyba.apirest.Usuario.Image.model.Image;

@Component
public class ImageBuilder {

    public Image builderImage(Image imageRecebida) {
        return Image
                .builder()
                .id(imageRecebida.getId())
                .name(imageRecebida.getName())
                .type(imageRecebida.getType())
                .imageData(imageRecebida.getImageData())
                .build();
    }
}
