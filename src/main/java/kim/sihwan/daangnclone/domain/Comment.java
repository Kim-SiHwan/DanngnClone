package kim.sihwan.daangnclone.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String username;
    private String content;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Builder
    public Comment (String username, String content){
        this.username = username;
        this.content = content;
        this.createDate = LocalDateTime.now();
    }

    public void addBoard(Board board){
        this.board = board;
        this.board.getComments().add(this);
    }


}
