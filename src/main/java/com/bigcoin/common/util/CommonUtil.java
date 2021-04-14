package com.bigcoin.common.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jackson.JsonNodeValueReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	// string 형의 JSON 값을 파싱	
		static JSONParser jsonParser;
		static ModelMapper modelMapper;
		static ObjectMapper objectMapper;

		/** 초기화 */
		static
		{
		    modelMapper = new ModelMapper();
		    modelMapper.getConfiguration().addValueReader(new JsonNodeValueReader());
		    // [songjaeho] 가져오는 json string의 변수명 케이스 타입 
		    modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
		    // [songjaeho] 매핑하고자 하는 필드 (대상 클래스)의 변수명 케이스 타입 
		    modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
		    jsonParser = new JSONParser();
		    
		    objectMapper = new ObjectMapper();
		}


		/**
		 * json 형태의 텍스트를 클래스로 변환하여 리턴
		 * @param jsonStr
		 *          json 포멧으로 되어있는 string(문자)
		 * @param obj
		 *          JSON 값을 매핑할 클래스
		 *
		 * 참조:
		 * - Jackson Integration (ModelMapper)
		 * http://modelmapper.org/user-manual/jackson-integration/
		 * - 문서상으로는 jsonNode 를 처리하도록 했는데, JSON의 형태가 2dept 이상 넘어가면 다음의 에러 생성
		 *      : at org.modelmapper.internal.Errors.throwMappingExceptionIfErrorsExist(Errors.java:374)
		 *  그래서 JsonNode 대신에 JSONObject 로 사용함.
		 */
		public static Object convertJsonStrToClass(String jsonStr , Class<?> obj){
		    try {
		        JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr);
		        return modelMapper.map(jsonObj, obj);
		    } catch (ParseException e) {
		        e.printStackTrace();
		        return null;
		    }
		}
		
		public static String makeJsonString(Object o) throws JsonProcessingException {
			return objectMapper.writeValueAsString(o);
		}
}
