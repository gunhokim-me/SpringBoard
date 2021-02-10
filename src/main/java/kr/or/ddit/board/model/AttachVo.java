package kr.or.ddit.board.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachVo {

	private int att_no;
	private int post_no;
	private int bor_num;
	private String user_id;
	private String file_nm;
	private String file_path;
	
}
