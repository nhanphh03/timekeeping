package proton.face.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataSearch {
	private String people_id;
	private String created_at;
	private String source;
	private double score;

}
