package idusw.springboot.lsmmall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "blogger")
public class BlogEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // MySQL, MariaDB의 경우 자동증가하는 필드 IDENTITY, Oracle의 경우 SEQUENCE, AUTO 유동적 선택
    private Long idx;
    @Column(length = 40, nullable = false)
    private String title;
    @Column(length = 200, nullable = false) // nullable = false 경우는 null 넣으면 오류 발생
    private String content;
    @Column
    private Long views = 0L;
    @Column(length = 20)
    private String block;
    // Association
    @ManyToOne(fetch = FetchType.LAZY) // 1 Blogger(Member)가 여러개의 블로그를 작성할 수 있는 관계
    @JoinColumn(name = "blogger_idx")
    private MemberEntity blogger;

    public void incrementViews() {
        if (this.views == null) {
            this.views = 0L;
        }
        this.views++;
    }
}