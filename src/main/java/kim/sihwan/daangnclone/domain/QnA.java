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
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;
    private String title;
    private String content;
    private boolean visited;
    private boolean answer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
