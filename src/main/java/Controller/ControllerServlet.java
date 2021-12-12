package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.net.HttpURLConnection;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.net.MalformedURLException;

import model.DBBean;
import model.HospInfo;
import model.MemberBean;
import model.ParmacyInfo;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/Controller")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8"); // 자바스크립트 한글 출력 코드
		HttpSession session = request.getSession();
		String type = request.getParameter("type");
		if (type == null || type.equals("")) {
			type = "index";
		}

		switch (type) {

		case "index": {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			break;
		}

		case "join": {
			RequestDispatcher dispatcher = request.getRequestDispatcher("join.jsp");
			dispatcher.forward(request, response);
			break;
		}

		case "joinPro": {
			MemberBean member = new MemberBean();
			DBBean instance = DBBean.getInstance();

			member.setName(request.getParameter("name"));
			member.setUser_id(request.getParameter("user_id"));
			member.setUser_pw(request.getParameter("user_pw"));
			member.setPhone(request.getParameter("phone"));
			member.setEmail(request.getParameter("email"));

			instance.insertMember(member);

			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			break;
		}

		case "login": {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			break;
		}

		case "loginPro": {
			DBBean instance = DBBean.getInstance();
			MemberBean member = new MemberBean();
			int check = 0;
			
			String user_id = request.getParameter("user_id");
			String user_pw = request.getParameter("user_pw");
			
			check = instance.userCheck(user_id, user_pw);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(check == 0) {
				out.println("<script type=\"text/javascript\"> ");
				out.println("alert('아이디 혹은 비밀번호가 맞지 않습니다.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.flush();
			}else{
				member = instance.getSession(user_id, user_pw);
				
				session.setAttribute("user_id", user_id);
				session.setAttribute("name", member.getName());
				session.setAttribute("email", member.getEmail());
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("Controller?type=index");
				dispatcher.forward(request, response);
				break;
			}
		}
		
		case "logOut": {
			session.invalidate();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "searchPro": {
			String lat = request.getParameter("latitude"); //현재 나의 위치를 가져옴 (위도)
			String longt = request.getParameter("longitude"); //현재 나의 위치를 가져옴 (경도)
			
			double latitude = Double.parseDouble(lat); //더블 타입으로 형변환
			double longitude = Double.parseDouble(longt); //더블 타입으로 형변환
			
			String apiURL = "https://openapi.gg.go.kr/Hospital?key=039ba542bd824e4f84afde262bad0049&type=json";
			//발급받은 병원정보 apiURL 정보
		
			Map<String, String> requestHeaders = new HashMap<>(); 
			
			String responseBody = get(apiURL, requestHeaders);
			
			
			//Json 파싱 시작
			JSONObject jObject = new JSONObject(); //json 데이터를 담을 객체 생성
			JSONParser jsonParser = new JSONParser(); //불러온 Json데이터 파싱 시작
			jObject = (JSONObject) jsonParser.parse(responseBody); //apiURL에서 가져온 데이터(병원정보)를 responseBody에서 파싱해서 jObject로 가져옴.
			
			
			JSONArray jsonArray = (JSONArray) jObject.get("Hospital"); //jObject에 저장된 json정보 중 Hospital 정보를 가져와서 jsonArray에 정보를 담음.
			JSONObject jsonRow = new JSONObject(); //jsonRow 객체 생성
			
			for(int i=0; i<=jsonArray.size(); i++) {
				jsonRow = (JSONObject) jsonArray.get(i); //jsonRow에 jsonArray에 탐색 됐던 Hospital 정보를 전부 탐색
				if(jsonRow.get("row") != null) break;
			}
			
			JSONArray jsonArrayData = (JSONArray) jsonRow.get("row"); //jsonArrayData에 jsonRow에서 가져온 Row(병원정보)를 담음.
			
			ArrayList<HospInfo> hospList = new ArrayList<HospInfo>(); //HospInfo 병원이름, 경도, 위도, 진료과목, 주소, 거리 정보를 담을 list 생성.
			
			for(int i=0; i < jsonArrayData.size(); i++) {
				JSONObject tempObject = (JSONObject) jsonArrayData.get(i); //탐색한 정보를 담을 tempObject를 생성
				HospInfo tempInfo = new HospInfo(); //출력할 병원의 각 정보를 담을 객체 생성
				
				tempInfo.setHospName((String) tempObject.get("BIZPLC_NM"));
	        	tempInfo.setLat((String) tempObject.get("REFINE_WGS84_LAT"));
	        	tempInfo.setLogt((String) tempObject.get("REFINE_WGS84_LOGT"));
	        	tempInfo.setAddress((String) tempObject.get("REFINE_ROADNM_ADDR"));
	        	tempInfo.setSubject((String) tempObject.get("TREAT_SBJECT_CONT"));
	        	//tempInfo에 파싱한 각 데이터 값을 담음
	        	
	        	double tempLogt = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LOGT"));
	        	double tempLat = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LAT"));
	        	
	        	double distanceKiloMeterDbl = distance(latitude, longitude, tempLat, tempLogt, "kilometer");
	        	
	        	tempInfo.setDistance(distanceKiloMeterDbl);
	        	
	        	hospList.add(tempInfo);
	        	
	        	request.setAttribute("hospList", hospList);
	        	
	        	
	        	
	        	
	        	Collections.sort(hospList, new Comparator<HospInfo>() {

					@Override
					public int compare(HospInfo o1, HospInfo o2) {
						Double dis1 = o1.getDistance();
						Double dis2 = o2.getDistance();
						return dis1.compareTo(dis2);
					}	
				});
			}
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
			dispatcher.forward(request, response);
			break;
		}
		case "Home":{
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "searchPro2":{
			String lat = request.getParameter("latitude2"); //현재 나의 위치를 가져옴 (위도)
			String longt = request.getParameter("longitude2"); //현재 나의 위치를 가져옴 (경도)
			
			double latitude = Double.parseDouble(lat); //더블 타입으로 형변환
			double longitude = Double.parseDouble(longt); //더블 타입으로 형변환
			
			String apiURL = "https://openapi.gg.go.kr/Parmacy?key=e1b2ae677015425ca85d742be61f6adc&type=json";
			
			Map<String, String> requestHeaders = new HashMap<>(); 
			
			String responseBody = get(apiURL, requestHeaders);
			
			//Json 파싱 시작
			JSONObject jObject = new JSONObject(); //json 데이터를 담을 객체 생성
			JSONParser jsonParser = new JSONParser(); //불러온 Json데이터 파싱 시작
			jObject = (JSONObject) jsonParser.parse(responseBody); //apiURL에서 가져온 데이터(병원정보)를 responseBody에서 파싱해서 jObject로 가져옴.
			
			
			JSONArray jsonArray = (JSONArray) jObject.get("Parmacy"); //jObject에 저장된 json정보 중 Hospital 정보를 가져와서 jsonArray에 정보를 담음.
			
			JSONObject jsonRow = new JSONObject(); //jsonRow 객체 생성
			
			for(int i=0; i<=jsonArray.size(); i++) {
				jsonRow = (JSONObject) jsonArray.get(i); //jsonRow에 jsonArray에 탐색 됐던 Hospital 정보를 전부 탐색
				if(jsonRow.get("row") != null) break;
			}
			
			JSONArray jsonArrayData = (JSONArray) jsonRow.get("row"); //jsonArrayData에 jsonRow에서 가져온 Row(병원정보)를 담음.
			
			
			ArrayList<ParmacyInfo> ParList = new ArrayList<ParmacyInfo>(); //HospInfo 병원이름, 경도, 위도, 진료과목, 주소, 거리 정보를 담을 list 생성.
			
			
			
			for(int i=0; i < jsonArrayData.size(); i++) {
				
				JSONObject tempObject = (JSONObject) jsonArrayData.get(i); //탐색한 정보를 담을 tempObject를 생성
				ParmacyInfo tempInfo = new ParmacyInfo(); //출력할 병원의 각 정보를 담을 객체 생성
				
				if (tempObject.get("REFINE_WGS84_LOGT") == null || tempObject.get("REFINE_WGS84_LAT") == null)
					continue;
				
				tempInfo.setParName((String) tempObject.get("BIZPLC_NM"));
	        	tempInfo.setLat((String) tempObject.get("REFINE_WGS84_LAT"));
	        	tempInfo.setLogt((String) tempObject.get("REFINE_WGS84_LOGT"));
	        	tempInfo.setAddress((String) tempObject.get("REFINE_ROADNM_ADDR"));
	        	//tempInfo에 파싱한 각 데이터 값을 담음
	        	
	        	double tempLogt = Double.parseDouble((String)tempObject.get("REFINE_WGS84_LOGT"));
	        	double tempLat = Double.parseDouble((String)tempObject.get("REFINE_WGS84_LAT"));
	        	
	        	double distanceKiloMeterDbl = distance(latitude, longitude, tempLat, tempLogt, "kilometer");
	        	
	        	tempInfo.setDistance(distanceKiloMeterDbl);
	        	
	        	ParList.add(tempInfo);
	        	
	        	request.setAttribute("ParList", ParList);
	        	
	        	Collections.sort(ParList, new Comparator<ParmacyInfo>() {

					@Override
					public int compare(ParmacyInfo o1, ParmacyInfo o2) {
						Double dis1 = o1.getDistance();
						Double dis2 = o2.getDistance(); 
						return dis1.compareTo(dis2);
					}	
				});
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("search2.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "content" : {
			
			
//			String lat = request.getParameter("latitude"); //현재 나의 위치를 가져옴 (위도)
//			String longt = request.getParameter("longitude"); //현재 나의 위치를 가져옴 (경도)
			
//			double latitude = Double.parseDouble(lat); //더블 타입으로 형변환
//			double longitude = Double.parseDouble(longt); //더블 타입으로 형변환
			
			String apiURL = "https://openapi.gg.go.kr/Hospital?key=039ba542bd824e4f84afde262bad0049&type=json";
			//발급받은 병원정보 apiURL 정보
		
			Map<String, String> requestHeaders = new HashMap<>(); 
			
			String responseBody = get(apiURL, requestHeaders);
			
			
			//Json 파싱 시작
			JSONObject jObject = new JSONObject(); //json 데이터를 담을 객체 생성
			JSONParser jsonParser = new JSONParser(); //불러온 Json데이터 파싱 시작
			jObject = (JSONObject) jsonParser.parse(responseBody); //apiURL에서 가져온 데이터(병원정보)를 responseBody에서 파싱해서 jObject로 가져옴.
			
			
			JSONArray jsonArray = (JSONArray) jObject.get("Hospital"); //jObject에 저장된 json정보 중 Hospital 정보를 가져와서 jsonArray에 정보를 담음.
			JSONObject jsonRow = new JSONObject(); //jsonRow 객체 생성
			
			for(int i=0; i<=jsonArray.size(); i++) {
				jsonRow = (JSONObject) jsonArray.get(i); //jsonRow에 jsonArray에 탐색 됐던 Hospital 정보를 전부 탐색
				if(jsonRow.get("row") != null) break;
			}
			
			JSONArray jsonArrayData = (JSONArray) jsonRow.get("row"); //jsonArrayData에 jsonRow에서 가져온 Row(병원정보)를 담음.
			
			ArrayList<HospInfo> hospList = new ArrayList<HospInfo>(); //HospInfo 병원이름, 경도, 위도, 진료과목, 주소, 거리 정보를 담을 list 생성.
			
			for(int i=0; i < jsonArrayData.size(); i++) {
				JSONObject tempObject = (JSONObject) jsonArrayData.get(i); //탐색한 정보를 담을 tempObject를 생성
				HospInfo tempInfo = new HospInfo(); //출력할 병원의 각 정보를 담을 객체 생성
				
				tempInfo.setHospName((String) tempObject.get("BIZPLC_NM"));
	        	tempInfo.setLat((String) tempObject.get("REFINE_WGS84_LAT"));
	        	tempInfo.setLogt((String) tempObject.get("REFINE_WGS84_LOGT"));
	        	tempInfo.setAddress((String) tempObject.get("REFINE_ROADNM_ADDR"));
	        	tempInfo.setSubject((String) tempObject.get("TREAT_SBJECT_CONT"));
	        	//tempInfo에 파싱한 각 데이터 값을 담음
	        	
//	        	double tempLogt = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LOGT"));
//	        	double tempLat = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LAT"));
	        	
//	        	double distanceKiloMeterDbl = distance(latitude, longitude, tempLat, tempLogt, "kilometer");
	        	
//	        	tempInfo.setDistance(distanceKiloMeterDbl);
	        	
	        	hospList.add(tempInfo);
	        	
	        	request.setAttribute("hospList", hospList);
	        	
	        	
	        	
	        	
	        	Collections.sort(hospList, new Comparator<HospInfo>() {

					@Override
					public int compare(HospInfo o1, HospInfo o2) {
						Double dis1 = o1.getDistance();
						Double dis2 = o2.getDistance();
						return dis1.compareTo(dis2);
					}	
				});
	        	
	        	
			}
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("content.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "content2" : {
			
			String apiURL = "https://openapi.gg.go.kr/Parmacy?key=e1b2ae677015425ca85d742be61f6adc&type=json";
			//발급받은 병원정보 apiURL 정보
		
			Map<String, String> requestHeaders = new HashMap<>(); 
			
			String responseBody = get(apiURL, requestHeaders);
			
			
			//Json 파싱 시작
			JSONObject jObject = new JSONObject(); //json 데이터를 담을 객체 생성
			JSONParser jsonParser = new JSONParser(); //불러온 Json데이터 파싱 시작
			jObject = (JSONObject) jsonParser.parse(responseBody); //apiURL에서 가져온 데이터(병원정보)를 responseBody에서 파싱해서 jObject로 가져옴.
			
			
			JSONArray jsonArray = (JSONArray) jObject.get("Parmacy"); //jObject에 저장된 json정보 중 Hospital 정보를 가져와서 jsonArray에 정보를 담음.
			JSONObject jsonRow = new JSONObject(); //jsonRow 객체 생성
			
			for(int i=0; i<=jsonArray.size(); i++) {
				jsonRow = (JSONObject) jsonArray.get(i); //jsonRow에 jsonArray에 탐색 됐던 Hospital 정보를 전부 탐색
				if(jsonRow.get("row") != null) break;
			}
			
			JSONArray jsonArrayData = (JSONArray) jsonRow.get("row"); //jsonArrayData에 jsonRow에서 가져온 Row(병원정보)를 담음.
			
			ArrayList<ParmacyInfo> ParList = new ArrayList<ParmacyInfo>(); //HospInfo 병원이름, 경도, 위도, 진료과목, 주소, 거리 정보를 담을 list 생성.
			
			for(int i=0; i < jsonArrayData.size(); i++) {
				JSONObject tempObject = (JSONObject) jsonArrayData.get(i); //탐색한 정보를 담을 tempObject를 생성
				ParmacyInfo tempInfo = new ParmacyInfo(); //출력할 병원의 각 정보를 담을 객체 생성
				
				if (tempObject.get("REFINE_WGS84_LOGT") == null || tempObject.get("REFINE_WGS84_LAT") == null)
					continue;
				
				tempInfo.setParName((String) tempObject.get("BIZPLC_NM"));
	        	tempInfo.setLat((String) tempObject.get("REFINE_WGS84_LAT"));
	        	tempInfo.setLogt((String) tempObject.get("REFINE_WGS84_LOGT"));
	        	tempInfo.setAddress((String) tempObject.get("REFINE_ROADNM_ADDR"));
	        	//tempInfo에 파싱한 각 데이터 값을 담음
	        	
//	        	double tempLogt = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LOGT"));
//	        	double tempLat = Double.parseDouble((String) tempObject.get("REFINE_WGS84_LAT"));
	        	
//	        	double distanceKiloMeterDbl = distance(latitude, longitude, tempLat, tempLogt, "kilometer");
	        	
//	        	tempInfo.setDistance(distanceKiloMeterDbl);
	        	
	        	ParList.add(tempInfo);
	        	
	        	request.setAttribute("ParList", ParList);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("content2.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		}

	}

	private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        
        double result1 = Math.round(dist * 100) / 100.0;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (result1);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private String get(String apiURL, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
	}

	private String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        } 
	}

	private HttpURLConnection connect(String apiURL) {
		try {
            URL url = new URL(apiURL);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
	}

}
