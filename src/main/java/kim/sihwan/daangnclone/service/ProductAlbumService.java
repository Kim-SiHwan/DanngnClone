package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.domain.ProductAlbum;
import kim.sihwan.daangnclone.dto.product.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductAlbumService {


    @Transactional
    public Product addProductAlbums(ProductRequestDto productRequestDto){
        Product product = productRequestDto.toEntity(productRequestDto);
        String fileUrl = "C:\\Users\\김시환\\Desktop\\Git\\DaangnClone\\src\\main\\resources\\static\\images\\";
        String saveUrl = "http://localhost:8080/api/product/download?fileName=";
        try{
            for(MultipartFile file : productRequestDto.getFiles()){
                String newFilename = createNewFilename(file.getOriginalFilename());
                saveUrl = saveUrl + newFilename;
                File dest = new File(fileUrl + newFilename);
                file.transferTo(dest);

                ProductAlbum productAlbum = ProductAlbum
                        .builder()
                        .filename(file.getOriginalFilename())
                        .url(saveUrl + newFilename)
                        .build();
                productAlbum.addProduct(product);

            }
            product.addThumbnail(saveUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

    public String createNewFilename(String filename){
        UUID uuid = UUID.randomUUID();
        String newFilename= uuid.toString() +"_" + filename;
        return newFilename;
    }


}
