package kim.sihwan.daangnclone.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_album_id")
    private Long id;
    private String url;
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    public ProductAlbum(String url, String filename){
        this.url = url;
        this.filename = filename;
    }

    public void addProduct(Product product){
        this.product = product;
        this.product.getProductAlbums().add(this);
    }
}
