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
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userIdx;

    @Column(name = "user_principal")
    private String userPrincipal;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_image")
    private String userImage;

    @Builder
    public User(String userPrincipal, String userName, String userId, String userImage) {
        this.userPrincipal = userPrincipal;
        this.userName = userName;
        this.userId = userId;
        this.userImage = userImage;
    }
}
