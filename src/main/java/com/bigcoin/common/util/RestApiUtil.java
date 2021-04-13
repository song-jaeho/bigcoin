package com.bigcoin.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiUtil {

	private static final int DEFAULT_TIMEOUT = 2000;
	
	public static String callGet(String paramUrl) throws IOException {
		
		String result = null;
		
		try {
			URL url = new URL(paramUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("X-Auth-Token", API_KEY);    
            
            conn.setConnectTimeout(DEFAULT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_TIMEOUT);
            conn.setRequestProperty("X-Data-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            
            // URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false
            conn.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				
			} else {
				System.out.println(conn.getResponseMessage());
			}
            conn.disconnect();
            
            result = sb.toString();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	
	public static String callPost(String paramUrl, String jsonStr) throws Exception {
		
		String result = null;
		
		try {
			URL url = new URL(paramUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEFAULT_TIMEOUT); //서버에 연결되는 Timeout 시간 설정
			conn.setReadTimeout(DEFAULT_TIMEOUT); // InputStream 읽어 오는 Timeout 시간 설정
			//con.addRequestProperty("x-api-key", RestTestCommon.API_KEY); //key값 설정

			conn.setRequestMethod("POST");

			// json 타입으로 데이터 전달
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoInput(true);
			conn.setDoOutput(true); // OutputStream으로 데이터를 넘김
			
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(jsonStr);
			wr.flush();

			StringBuilder sb = new StringBuilder();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
			} else {
				System.out.println(conn.getResponseMessage());
			}
			conn.disconnect();
			
			result = sb.toString();
			
		} catch (Exception e){
			System.err.println(e.toString());
			throw e;
		}
		
		return result;
	}
}
