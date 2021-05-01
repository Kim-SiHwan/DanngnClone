package kim.sihwan.daangnclone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;
    private String title;
    private String content;
    private boolean visited;
    private boolean answer;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void visitedQna(){
        this.visited=true;
    }

    public void answeredQna(){
        this.answer=true;
    }

    @Builder
    public Qna(String title, String content, boolean visited, boolean answer) {
        this.title = title;
        this.content = content;
        this.visited = visited;
        this.answer = answer;
        this.createDate = LocalDateTime.now();
    }

    public void addMember(Member member) {
        this.member = member;
    }
}
