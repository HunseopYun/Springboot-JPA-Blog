package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@Getter
//@Setter
@Data // getter setter 동시 사용
//@AllArgsConstructor // 컨스럭트 사용
@NoArgsConstructor
//@RequiredArgsConstructor
public class Member {
	//final 셋팅 이유, 디비에서 가져온 값을 셋팅하는것이고 그 디비에서 가져온 값을 수정할 이유는 없으므로 
	// 혹시나 수정될 수 있는 것을 방지하기 위한 안전장치로서 final 선언
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
	
}
