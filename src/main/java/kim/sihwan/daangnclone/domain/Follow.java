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
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member toMember;

    public void addFromMember(Member member){
        this.fromMember = member;
    }
    public void addToMember(Member member){
        this.toMember = member;
    }
}
