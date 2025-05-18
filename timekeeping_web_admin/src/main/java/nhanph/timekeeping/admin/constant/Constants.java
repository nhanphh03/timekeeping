package nhanph.timekeeping.admin.constant;


import nhanph.timekeeping.admin.util.ConfProperties;

public class Constants {

	public static final String URL_FACE_REGISTER = ConfProperties.getProperty("url.register.face.prt");
	public static final String URL_FACE_SEARCH = ConfProperties.getProperty("url.search.face.prt");
	public static final String IMAGE_PATH = "/home/dell/quyennb/KVCProduct/image_face/";
	public static final String SOURCE = "timekeeping_face";
	public static final String URL_IMAGE = ConfProperties.getProperty("service.get.url")	;
	public static final String EXCEL_TEMPLATE_PATH = ConfProperties.getProperty("excel_template_path.prt");

	public static class FaceResponse {
		public static final String FACE_EXISTED = "Khuôn mặt đã tồn tại trong cơ sở dữ liệu !";
	}

	public static class FaceResponseCodes {
		public static final String FACE_EXISTED_RES = "FACE_EXISTED_RES";
		public static final String REGISTER_FACE_SUCCESS_RES = "REGISTER_FACE_SUCCESS_RES";
		public static final String REGISTER_FACE_FAIL_RES = "REGISTER_FACE_FAIL_RES";
		public static final String DUPLICATE_REGISTER_RES = "DUPLICATE_REGISTER";
		public static final String FACE_NOT_EXISTED_RES = "FACE_NOT_EXISTED_RES";
	}

	public static class CallAPIResponse {
		public static final String ERROR_FACE = "Lỗi gọi sang hệ thống khác !";
		public static final String REGISTER_FACE_FAIL = "Thêm khuôn mặt thất bại !";
		public static final String INPUT_ERROR = "Định dạng hình ảnh đầu vào không đúng.";
	}
	public static class CallAPIResponseCodes {
		public static final String INPUT_ERROR_RES = "INPUT_ERROR";
	}


}
