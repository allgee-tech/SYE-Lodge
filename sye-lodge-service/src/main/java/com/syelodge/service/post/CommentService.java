package com.syelodge.service.post;

import com.syelodge.common.dto.CommentDto;
import com.syelodge.common.dto.CommentRequest;
import com.syelodge.common.dto.PagedResponse;
import com.syelodge.common.enums.ModerationStatus;
import com.syelodge.common.enums.UserRole;
import com.syelodge.common.exception.BadRequestException;
import com.syelodge.common.exception.ForbiddenException;
import com.syelodge.common.exception.NotFoundException;
import com.syelodge.common.exception.UnauthorizedException;
import com.syelodge.persistence.entity.Comment;
import com.syelodge.persistence.entity.StudentPost;
import com.syelodge.persistence.entity.User;
import com.syelodge.persistence.repository.CommentRepository;
import com.syelodge.persistence.repository.StudentPostRepository;
import com.syelodge.service.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/** Reads and writes comments on student request threads. */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final StudentPostRepository postRepository;

    public CommentService(CommentRepository commentRepository, StudentPostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public PagedResponse<CommentDto> list(User requester, UUID postId, int page, int size) {
        requireBoardAccess(requester);
        StudentPost post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("That student post could not be found."));

        boolean admin = requester != null && requester.getRole() == UserRole.ADMIN
                && requester.isAdminVerified();
        boolean author = requester != null && post.getAuthor().getId().equals(requester.getId());
        if (post.getStatus() != ModerationStatus.VISIBLE && !admin && !author) {
            throw new NotFoundException("That student post could not be found.");
        }

        PageRequest pageRequest = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Comment> result = admin
                ? commentRepository.findByPostIdOrderByCreatedAtAsc(postId, pageRequest)
                : commentRepository.findByPostIdAndStatusOrderByCreatedAtAsc(postId, ModerationStatus.VISIBLE,
                pageRequest);
        List<CommentDto> content = result.getContent().stream().map(DtoMapper::comment).toList();
        return PagedResponse.of(content, result.getNumber(), result.getSize(), result.getTotalElements());
    }

    @Transactional
    public CommentDto add(User author, UUID postId, CommentRequest request) {
        requireBoardAccess(author);
        StudentPost post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("That student post could not be found."));
        if (post.getStatus() != ModerationStatus.VISIBLE) {
            throw new BadRequestException("You cannot comment on a post that is not visible.");
        }
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setBody(request.body().trim());
        comment.setStatus(ModerationStatus.VISIBLE);
        commentRepository.save(comment);
        return DtoMapper.comment(comment);
    }

    @Transactional
    public void removeOwn(User author, UUID commentId) {
        requireBoardAccess(author);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("That comment could not be found."));
        boolean isAuthor = comment.getAuthor().getId().equals(author.getId());
        boolean isAdmin = author.getRole() == UserRole.ADMIN && author.isAdminVerified();
        if (!isAuthor && !isAdmin) {
            throw new ForbiddenException("You can only delete your own comments.");
        }
        comment.setStatus(ModerationStatus.REMOVED);
        commentRepository.save(comment);
    }

    /** Only students and administrators can read or take part in board conversations. */
    private void requireBoardAccess(User requester) {
        if (requester == null) {
            throw new UnauthorizedException("Please sign in to view the student board.");
        }
        if (requester.getRole() != UserRole.STUDENT && requester.getRole() != UserRole.ADMIN) {
            throw new ForbiddenException("The student board is only available to students.");
        }
    }
}
