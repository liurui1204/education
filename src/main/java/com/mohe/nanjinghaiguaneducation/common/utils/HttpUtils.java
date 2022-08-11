package com.mohe.nanjinghaiguaneducation.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * Get方式请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpGet(String url, Map<String, String> params){
		//创建HttpClient对象
		HttpClient client = HttpClients.createDefault();
		//定义返回对象
		String body = null;
		try {
			logger.info("Get请求地址：" + url);
			logger.info("Get请求参数：" + (params == null ? null : params.toString()));
			//设置装填参数
			if (params != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : params.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps), "UTF-8");
			}
			//创建get方式请求对象
			HttpGet httpGet = new HttpGet(url);
			//执行请求操作，并拿到结果（同步阻塞）
			HttpResponse response = client.execute(httpGet);
			//获取请求码
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("Get结果状态：" + statusCode);
			if (statusCode == 200) {
				//获取结果实体
				HttpEntity entity = response.getEntity();
				//按指定编码转换结果实体为String类型，并返回
				body = EntityUtils.toString(entity, "UTF-8");
				logger.info("Get请求结果：" + body);
			}
		} catch (Exception e) {
			//TODO Auto-generated catch block
			logger.info("Get请求失败：", e);
		}
		return body;
	}
	
	/**
	 * Post方式请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> params){
		//创建HttpClient对象
		HttpClient client = HttpClients.createDefault();
		//定义返回对象
		String body = null;
        try {
        	logger.info("Post请求地址：" + url);
        	logger.info("Post请求参数：" + params==null?null:params.toString());
    		//创建post方式请求对象
    		HttpPost httpPost = new HttpPost(url);
    		//设置装填参数
            if(params!=null){
            	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                //设置参数到请求对象中
    			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
			//指定报文头【Content-type】
	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
	        //执行请求操作，并拿到结果（同步阻塞）
	        HttpResponse response = client.execute(httpPost);
	        //获取请求码
 			int statusCode = response.getStatusLine().getStatusCode();
 			logger.info("Post结果状态：" + statusCode);
 			if (statusCode == 200) {
 				//获取结果实体
 		        HttpEntity entity = response.getEntity();
 		        //按指定编码转换结果实体为String类型，并返回
 		        body = EntityUtils.toString(entity, "UTF-8");
 		        logger.info("Post请求结果：" + body);
 			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Post请求失败：" , e);
		}
        return body;
	}
	
	/**
	 * Post方式请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url, String params){
		//创建HttpClient对象
		HttpClient client = HttpClients.createDefault();
		//定义返回对象
		String body = null;
		try {
			logger.info("Post请求地址：" + url);
			if(params!=null){
				logger.info("Post请求参数：" + params);
			}
    		//创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8");
			//设置装填参数
			StringEntity entity = new StringEntity(params, Charset.forName("UTF-8"));
			//指定报文头编码
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setEntity(entity);
			HttpResponse response=client.execute(httpPost);
			//获取请求码
 			int statusCode = response.getStatusLine().getStatusCode();
 			logger.info("Post结果状态：" + statusCode);
 			if (statusCode == 200) {
 				//获取结果实体
 		        HttpEntity responseEntity = response.getEntity();
 		        //按指定编码转换结果实体为String类型，并返回
 		        body = EntityUtils.toString(responseEntity, "UTF-8");
 		        logger.info("Post请求结果：" + body);
 			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Post请求失败：" , e);
		}
		return body;
	}

	/**
	 * post form
	 *
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method,
									  Map<String, String> headers,
									  Map<String, String> querys,
									  Map<String, String> bodys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (bodys != null) {
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

			for (String key : bodys.keySet()) {
				nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			request.setEntity(formEntity);
		}

		return httpClient.execute(request);
	}

	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(host);
		if (!StringUtils.isBlank(path)) {
			sbUrl.append(path);
		}
		if (null != querys) {
			StringBuilder sbQuery = new StringBuilder();
			for (Entry<String, String> query : querys.entrySet()) {
				if (0 < sbQuery.length()) {
					sbQuery.append("&");
				}
				if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
					sbQuery.append(query.getValue());
				}
				if (!StringUtils.isBlank(query.getKey())) {
					sbQuery.append(query.getKey());
					if (!StringUtils.isBlank(query.getValue())) {
						sbQuery.append("=");
						sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
					}
				}
			}
			if (0 < sbQuery.length()) {
				sbUrl.append("?").append(sbQuery);
			}
		}

		return sbUrl.toString();
	}

	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}

		return httpClient;
	}

	private static void sslClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] xcs, String str) {

				}
				public void checkServerTrusted(X509Certificate[] xcs, String str) {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}


	public static HttpResponse doGet(String host, String path, String method,
									 Map<String, String> headers,
									 Map<String, String> querys)
			throws Exception {
		HttpClient httpClient = wrapClient(host);

		HttpGet request = new HttpGet(buildUrl(host, path, querys));
		for (Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		return httpClient.execute(request);
	}
}
