package com.cos.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	// dto의 변수들의 순서에 맞게 여기의 쿼리도 순서를 신경써서 넣어야함
	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery=true)
	int mSave(int userId, int boardId, String content); // 업데이트된 행의 갯수를 리턴해주기 때문에 인트로 받아야함
}