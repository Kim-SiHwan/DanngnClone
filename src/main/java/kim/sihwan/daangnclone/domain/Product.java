package kim.sihwan.daangnclone.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String area;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private String thumbnail;
    private int read;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductAlbum> productAlbums = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductTag> productTags = new HashSet<>();

    @OneToMany(mappedBy="product",cascade =CascadeType.REMOVE)
    private Set<ProductInterested> productInteresteds = new HashSet<>();


    @Builder
    public Product(String area, String username,String nickname, String thumbnail, String title, String content) {
        this.area = area;
        this.username = username;
        this.nickname = nickname;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.read = 0;
        this.createDate = LocalDateTime.now();
    }

    public void addRead(){
        this.read++;
    }

    public void addMember(Member member){
        this.member =member;
    }

    public void addThumbnail(String thumbnailUrl) {
        this.thumbnail = thumbnailUrl;
    }



}
