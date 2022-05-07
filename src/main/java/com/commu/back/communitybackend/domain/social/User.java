package com.commu.back.communitybackend.domain.social;

import com.commu.back.communitybackend.domain.posts.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", indexes = {@Index(name = "ix_user_principal", columnList = "user_principal", unique = true)})
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userIdx;

    @Column(name = "user_principal")
    private String userPrincipal;

    @Column(name = "screen_name")
    private String screenName;  // dev-latte

    @Column(name = "display_name")
    private String displayName; // @dev_latte

    @Column(name = "user_image")
    private String userImage;

    @Builder
    public User(String userPrincipal, String screenName, String displayName, String userImage) {
        this.userPrincipal = userPrincipal;
        this.screenName = screenName;
        this.displayName = displayName;
        this.userImage = userImage;
    }
}
