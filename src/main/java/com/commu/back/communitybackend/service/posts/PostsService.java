package com.commu.back.communitybackend.service.posts;

import com.commu.back.communitybackend.domain.posts.Posts;
import com.commu.back.communitybackend.domain.posts.PostsRepository;
import com.commu.back.communitybackend.web.dto.PostsResponseDto;
import com.commu.back.communitybackend.web.dto.PostsUpdateRequestDto;
import com.commu.back.communitybackend.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

}
