package com.cos.blog.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService {

	/*
	 * @Autowired를 어노테이션없이 풀어서 기술하면 아래와 같다.
	 * 
	 * private BoardRepository boardRepository;
	 * private ReplyRepository replyRepository;
	 * 
	 * public BoardService(BoardRepository bRepo, ReplyRepository rRepo){
	 * 		this.boardRepository = bRepo;
	 * 		this.replyRepository = rRepo;
	 * }
	 * 
	 */
	
//	@Autowired // DI
//	private BoardRepository boardRepository;
//	
//	@Autowired
//	private ReplyRepository replyRepository;
	
	// 위의 방법보다 더 간단하게 DI하는 법
	// 아래와 같이 파이널 선언을 해주고 나면 초기화를 해주어함
	// 그래서 메인 함수에 어노테이션 @RequiredArgsConstructor 을 붙혀줌으로서 초기화시켜줌
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) {  // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pagealbe){
		return boardRepository.findAll(pagealbe);
	} 
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수가 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 떄) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가 됨 db flush
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		/* 아래와 같이 영속화를 하여 구현할 수도 있음
		User user = userRepository.findById(replySaveRequestDto.getUserId())
				.orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다.");
		});
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
				.orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
		});
		
		Reply reply = Reply.builder()
				.board(board)
				.user(user)
				.content(replySaveRequestDto.getContent())
				.build();
				*/
		
		// 위의 빌더를 이용하지 않고 좀더 편리하게
		//		Reply reply = new Reply();
		//		reply.update(user, board, replySaveRequestDto.getContent());
		
		//replyRepository.save(reply);
		
		
		// 네이티브 쿼리를 하용해서 간편하게 가능, 인터페이스에서 네이티브 쿼리를 작성하여 영속화를 하지 않고 편하게 처리
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	
}
