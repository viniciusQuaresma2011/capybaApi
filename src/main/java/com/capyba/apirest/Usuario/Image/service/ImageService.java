package com.capyba.apirest.Usuario.Image.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.capyba.apirest.Usuario.Image.model.Image;
import com.capyba.apirest.Usuario.Image.model.builder.ImageBuilder;
import com.capyba.apirest.Usuario.Image.repository.ImageRepository;
import com.capyba.apirest.Usuario.Image.util.ImageUtils;

@Service
public class ImageService {

    @Autowired
    private ImageBuilder builder;

    @Autowired
    private ImageRepository imgRepository;

    public List<Image> listarImages() {
        return imgRepository.findAll();
    }

    @Transactional
    public Image salvarImage(MultipartFile file) throws IOException {

        Image imageNova = uploadImage(file);

        var imageRecebida = builder.builderImage(imageNova);
        var imageSalva = imgRepository.save(imageRecebida);
        return imageSalva;
    }

    public Image uploadImage(MultipartFile file) throws IOException {

        Image imageData = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build();

        return imageData;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imgRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Transactional
    public void removerImage(Long id) {
        imgRepository.deleteById(id);
    }

}
