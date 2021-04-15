package com.bigcoin.common.util;

import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jackson.JsonNodeValueReader;

import com.bigcoin.dto.BithumbData;
import com.bigcoin.dto.CurrencyData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
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
		    objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
		
		public static BithumbData convertJsonToBithumbData(String jsonStr) throws JsonMappingException, JsonProcessingException {
			
			BithumbData resultData = new BithumbData();
			JsonNode node = objectMapper.readTree(jsonStr);
			
			String status = node.get("status").asText();
			resultData.setStatus(status);
			
			// status가 "0000"이 아니면 실패임 
			if (status.equals("0000")) {
				
				// 노드를 data부로 옮긴다.
				node = node.get("data");

				Iterator<Entry<String, JsonNode>> it = node.fields();
				while(it.hasNext()) {
					Entry<String, JsonNode> entry = it.next();
					
					if (entry.getKey().equals("date")) {
						resultData.setDate(entry.getValue().asText());
						continue; // 혹은 break; (맨마지막이니까)
					}
					
					CurrencyData currencyData = objectMapper.readValue(entry.getValue().toString(), CurrencyData.class);
					currencyData.setTicker(entry.getKey());
					
					resultData.getData().add(currencyData);
				}
			} else {
				// TODO 실패하면 어쩌지..?
			}
			
			return resultData;
		}
}
