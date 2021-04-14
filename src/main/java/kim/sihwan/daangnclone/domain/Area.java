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
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;
    private String address;
    private String city;
    private String gu;
    private String dong;
    private String lat;
    private String lng;

    @Builder
    public Area(String address, String city, String gu, String dong, String lat, String lng){
        this.address=address;
        this.city=city;
        this.gu=gu;
        this.dong=dong;
        this.lat=lat;
        this.lng=lng;
    }
}
