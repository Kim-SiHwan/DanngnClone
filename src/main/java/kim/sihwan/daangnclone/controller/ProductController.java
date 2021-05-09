package kim.sihwan.daangnclone.controller;

import kim.sihwan.daangnclone.dto.product.ProductListResponseDto;
import kim.sihwan.daangnclone.dto.product.ProductRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductResponseDto;
import kim.sihwan.daangnclone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void addProduct(@ModelAttribute ProductRequestDto productRequestDto){
        productService.addProduct(productRequestDto);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> gg(@PathVariable Long productId){
        return new ResponseEntity<>(productService.pushInterest( productId),HttpStatus.OK);
    }

    @GetMapping("/all/{tagId}")
    public ResponseEntity<List<ProductListResponseDto>> getProducts(@PathVariable Long tagId){
        List<ProductListResponseDto> list = productService.findAllProductsByTagId(tagId);
        return new ResponseEntity<>(list,HttpStatus.OK);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId){
        ProductResponseDto productResponseDto = ProductResponseDto.toDto(productService.findById(productId));
        return new ResponseEntity(productResponseDto,HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
        Resource resource = new FileSystemResource("C:\\Users\\김시환\\Desktop\\Git\\DaangnClone\\src\\main\\resources\\static\\images\\" + fileName);
        return new ResponseEntity(resource, HttpStatus.OK);
    }




}
