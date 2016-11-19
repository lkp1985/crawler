package com.httpclient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** ��Ӧ�����װ
 * @author ��³�
 * @date 2015��7��17��
 */
public class ResponseWrap {
	private Logger logger = LoggerFactory.getLogger(ResponseWrap.class);
	
	private CloseableHttpResponse response;
	private CloseableHttpClient httpClient;
	private HttpEntity entity;
	private HttpRequestBase request;
	private HttpClientContext context;
	private static ObjectMapper mapper;
	
	public ResponseWrap(CloseableHttpClient httpClient, HttpRequestBase request, CloseableHttpResponse response, HttpClientContext context, ObjectMapper _mapper){
		this.response = response;
		this.httpClient = httpClient;
		this.request = request;
		this.context = context;
		mapper = _mapper;
		
		try {
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				this.entity =  new BufferedHttpEntity(entity);
			} else {
				this.entity = new BasicHttpEntity();
			}
			
			EntityUtils.consumeQuietly(entity);
			this.response.close();
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}
	

	/**
	 * ��ֹ����
	 * @author ��³�
	 * @date 2015��7��18��
	 */
	public void abort(){
		request.abort();
	}
	
	/**
	 * ��ȡ�ض���ĵ�ַ
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public List<URI> getRedirectLocations(){
		return context.getRedirectLocations();
	}
	
	/**
	 * �ر�����
	 * @author ��³�
	 * @date 2015��7��18��
	 */
	@SuppressWarnings("deprecation")
	public void shutdown(){
		httpClient.getConnectionManager().shutdown();
	}
	
	/**
	 * ��ȡ��Ӧ����ΪString,Ĭ�ϱ���Ϊ "UTF-8"
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public String getString() {
		return getString(Consts.UTF_8);
	}
	
	/**
	 * ��ȡ��Ӧ����ΪString
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param defaultCharset ָ������
	 * @return
	 */
	public String getString(Charset defaultCharset) {
		try {
			return EntityUtils.toString(entity, defaultCharset);
		} catch (ParseException | IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * ��ȡ��Ӧ������
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public Header getContentType() {
		return entity.getContentType();
	}
	
	/**
	 * ��ȡ��Ӧ����,������ı��Ļ�
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public Charset getCharset() {
		 ContentType contentType = ContentType.get(entity);
		 if(contentType == null) return null;
		 return contentType.getCharset();
	}
	
	/**
	 * ��ȡ��Ӧ����Ϊ�ֽ�����
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public byte[] getByteArray() {
		try {
			return EntityUtils.toByteArray(entity);
		} catch (ParseException | IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * ��ȡ����Header
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public Header[] getAllHeaders() {
		return response.getAllHeaders();
	}
	
	/**
	 * ��ȡ֪�����Ƶ�Header�б�
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public Header[] getHeaders(String name) {
		return response.getHeaders(name);
	}
	
	/**
	 * ��ȡ��Ӧ״̬��Ϣ
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public StatusLine getStatusLine(){
		return response.getStatusLine();
	}
	
	/**
	 * �Ƴ�ָ��name��Header�б�
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param name
	 */
	public void removeHeaders(String name){
		response.removeHeaders(name);
	}
	
	/**
	 * �Ƴ�ָ����Header
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param header
	 */
	public void removeHeader(Header header){
		response.removeHeader(header);
	}
	
	/**
	 * �Ƴ�ָ����Header
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param name
	 * @param value
	 */
	public void removeHeader(String name, String value){
		response.removeHeader(new BasicHeader(name, value));
	}
	
	/**
	 * �Ƿ����ָ��name��Header
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param name
	 * @return
	 */
	public boolean containsHeader(String name){
		return response.containsHeader(name);
	}
	
	/**
	 * ��ȡHeader�ĵ�����
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public HeaderIterator headerIterator(){
		return response.headerIterator();
	}

	/**
	 * ��ȡЭ��汾��Ϣ
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public ProtocolVersion getProtocolVersion(){
		return response.getProtocolVersion();
	}
	
	/**
	 * ��ȡCookieStore
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public CookieStore getCookieStore(){
		return context.getCookieStore();
	}
	
	/**
	 * ��ȡCookie�б�
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public List<Cookie> getCookies(){
		return getCookieStore().getCookies();
	}
	
	/**
	 * ��ȡInputStream,��Ҫ�ֶ��ر���
	 * @author ��³�
	 * @date 2015��7��17��
	 * @return
	 */
	public InputStream getInputStream(){
		try {
			return entity.getContent();
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * ��ȡBufferedReader
	 * @author ��³�
	 * @date 2015��7��18��
	 * @return
	 */
	public BufferedReader getBufferedReader(){
		return new BufferedReader(new InputStreamReader(getInputStream(), getCharset()));
	}
	
	/**
	 * ��Ӧ����д�뵽�ļ�
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param filePth ·��
	 */
	public void transferTo(String filePth) {
		transferTo(new File(filePth));
	}
	
	/**
	 * ��Ӧ����д�뵽�ļ�
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param file
	 */
	public void transferTo(File file) {
		try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
			transferTo(fileOutputStream);
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * д�뵽OutputStream,������ر�OutputStream
	 * @author ��³�
	 * @date 2015��7��17��
	 * @param outputStream OutputStream
	 */
	public void transferTo(OutputStream outputStream) {
		try {
			entity.writeTo(outputStream);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * ��ȡJSON����
	 * @author ��³�
	 * @date 2015��7��24��
	 * @param clazz
	 * @return
	 */
	public <T> T getJson(Class<T> clazz) {
		try {
			return mapper.readValue(getByteArray(), clazz);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * ��Jsonת����List
	 * @author ��³�
	 * @date 2015��7��24��
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getJsonList(Class<?> clazz) {
		try {
			return mapper.readValue(getByteArray(), new TypeReference<List<T>>() {});
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}