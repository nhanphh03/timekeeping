package proton.face.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

@Getter
@Setter
public class DataResponseFromCheckFacesSearch {

	private double SCORE_IS_FACE_EXIST = 0.8;

	private String status;
	private DataSearch[] data;

	public String getPeopleIdOfFaceV2() {

		DataSearch firstData;
		if (ArrayUtils.isEmpty(data)) {
			return null;
		} else {
			firstData = data[0];
			if (firstData.getScore() >= SCORE_IS_FACE_EXIST) {
				return firstData.getPeople_id();
			} else {
				return null;
			}
		}
	}

}
