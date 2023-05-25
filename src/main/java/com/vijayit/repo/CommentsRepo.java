package com.vijayit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijayit.entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
