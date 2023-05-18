package at.qe.backend.repositories;

import at.qe.backend.models.UploadImages;

import java.util.List;

public interface UploadImagesRepository extends AbstractRepository<UploadImages, Long>{
    List<UploadImages> getAllUploadLinksByPlantId(Long id);
    UploadImages findUploadImagesByUploadLink(String url);
}
