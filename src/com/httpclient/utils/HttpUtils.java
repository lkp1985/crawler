package com.httpclient.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkp.common.net.HttpProxy;

/**
 * 鍩轰簬httpclient 4.3x灏佽鐨刪ttp璇锋眰鎿嶄綔
 * @author 椹痉鎴�
 * @date 2015骞�8鏈�20鏃�
 */
public class HttpUtils {
	private final String IP="118.181.37.59";
    private final int PORT=8998;
			

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	private HttpRequestBase request; //璇锋眰瀵硅薄
	private EntityBuilder builder; //Post, put璇锋眰鐨勫弬鏁�
	private URIBuilder uriBuilder; //get, delete璇锋眰鐨勫弬鏁�
	private LayeredConnectionSocketFactory socketFactory; //杩炴帴宸ュ巶
	private HttpClientBuilder clientBuilder; //鏋勫缓httpclient
	private MultipartEntityBuilder partBuilder;
	private CloseableHttpClient httpClient; //
	private CookieStore cookieStore; //cookie瀛樺偍鍣�
	private Builder config; //璇锋眰鐨勭浉鍏抽厤缃�
	private boolean isHttps; //鏄惁鏄痟ttps璇锋眰
	private int type; //璇锋眰绫诲瀷1-post, 2-get, 3-put, 4-delete
	public static HttpHost proxy ;
	private HttpUtils (HttpRequestBase request) {
		this.request = request;
		
		this.clientBuilder = HttpClientBuilder.create();
		this.isHttps = request.getURI().getScheme().equalsIgnoreCase("https");
 	HttpHost proxy =// HttpProxy.getHttpProxy();
 			HttpProxy. getProxy("139.129.133.235","3000");
//		System.out.println("proxy="+proxy);
		this.config = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY) .setProxy(proxy);
 		//	.setProxy(new HttpHost("139.129.133.235",3000));
				;
		this.cookieStore = new BasicCookieStore();
		
		if (request instanceof HttpPost) {
			this.type = 1;
			this.builder = EntityBuilder.create().setParameters(new ArrayList<NameValuePair>());
			
		} else if(request instanceof HttpGet) {
			this.type = 2;
			this.uriBuilder = new URIBuilder();
			
		} else if(request instanceof HttpPut) {
			this.type = 3;
			this.builder = EntityBuilder.create().setParameters(new ArrayList<NameValuePair>());
		
		} else if(request instanceof HttpDelete) {
			this.type = 4;
			this.uriBuilder = new URIBuilder();
		}
	}
	
	private HttpUtils(HttpRequestBase request, HttpUtils clientUtils) {
		this(request);
		this.httpClient = clientUtils.httpClient;
		this.config = clientUtils.config;
		this.setHeaders(clientUtils.getAllHeaders());
		this.SetCookieStore(clientUtils.cookieStore);
	}
	
	private static HttpUtils create(HttpRequestBase request) {
		return new HttpUtils(request);
	}
	
	private static HttpUtils create(HttpRequestBase request, HttpUtils clientUtils) {
		return new HttpUtils(request, clientUtils);
	}

	/**
	 * 鍒涘缓post璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils post(String url) {
		return create(new HttpPost(url));
	}
	
	/**
	 * 鍒涘缓get璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils get(String url) {
		return create(new HttpGet(url));
	}
	
	/**
	 * 鍒涘缓put璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils put(String url) {
		return create(new HttpPut(url));
	}
	
	/**
	 * 鍒涘缓delete璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils delete(String url) {
		return create(new HttpDelete(url));
	}
	
	/**
	 * 鍒涘缓post璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils post(URI uri) {
		return create(new HttpPost(uri));
	}
	
	/**
	 * 鍒涘缓get璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils get(URI uri) {
		return create(new HttpGet(uri));
	}
	
	/**
	 * 鍒涘缓put璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils put(URI uri) {
		return create(new HttpPut(uri));
	}
	
	/**
	 * 鍒涘缓delete璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils delete(URI uri) {
		return create(new HttpDelete(uri));
	}
	
	/**
	 * 鍒涘缓post璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils post(String url, HttpUtils clientUtils) {
		return create(new HttpPost(url), clientUtils);
	}
	
	/**
	 * 鍒涘缓get璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils get(String url, HttpUtils clientUtils) {
		return create(new HttpGet(url), clientUtils);
	}
	
	/**
	 * 鍒涘缓put璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils put(String url, HttpUtils clientUtils) {
		return create(new HttpPut(url), clientUtils);
	}
	
	/**
	 * 鍒涘缓delete璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param url 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils delete(String url, HttpUtils clientUtils) {
		return create(new HttpDelete(url), clientUtils);
	}
	
	/**
	 * 鍒涘缓post璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils post(URI uri, HttpUtils clientUtils) {
		return create(new HttpPost(uri), clientUtils);
	}
	
	/**
	 * 鍒涘缓get璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils get(URI uri, HttpUtils clientUtils) {
		return create(new HttpGet(uri), clientUtils);
	}
	
	/**
	 * 鍒涘缓put璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils put(URI uri, HttpUtils clientUtils) {
		return create(new HttpPut(uri), clientUtils);
	}
	
	/**
	 * 鍒涘缓delete璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param uri 璇锋眰鍦板潃
	 * @return
	 */
	public static HttpUtils delete(URI uri, HttpUtils clientUtils) {
		return create(new HttpDelete(uri), clientUtils);
	}
	
	/**
	 * 娣诲姞鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param parameters
	 * @return
	 */
	public HttpUtils setParameters(final NameValuePair ...parameters) {
		if (builder != null) {
			builder.setParameters(parameters);
		} else {
			uriBuilder.setParameters(Arrays.asList(parameters));
		}
		return this;
	}
	
	/**
	 * 娣诲姞鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpUtils addParameter(final String name, final String value) {
		if (builder != null) {
			builder.getParameters().add(new BasicNameValuePair(name, value));
		} else {
			uriBuilder.addParameter(name, value);
		}
		return this;
	}
	
	/**
	 * 娣诲姞鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpUtils addParameter(final String name, final Object value) {
		String parameter = value == null? null : value.toString();
		
		if (builder != null) {
			builder.getParameters().add(new BasicNameValuePair(name, parameter));
		} else {
			uriBuilder.addParameter(name, parameter);
		}
		return this;
	}
	
	/**
	 * 娣诲姞鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param parameters
	 * @return
	 */
	public HttpUtils addParameters(final NameValuePair ...parameters) {
		if (builder != null) {
			builder.getParameters().addAll(Arrays.asList(parameters));
		} else {
			uriBuilder.addParameters(Arrays.asList(parameters));
		}
		return this;
	}
	
	/**
	 * 娣诲姞鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param parameters
	 * @return
	 */
	public HttpUtils addParameters(final Map<String, ?> parameters) {
		if (builder != null) {
			builder.getParameters().addAll(convertNameValuePair(parameters));
		} else {
			uriBuilder.addParameters(convertNameValuePair(parameters));
		}
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param parameters
	 * @return
	 */
	public HttpUtils setParameters(final Map<String, ?> parameters) {
		if (builder != null) {
			builder.setParameters(convertNameValuePair(parameters));
		} else {
			uriBuilder.setParameters(convertNameValuePair(parameters));
		}
		return this;
	}
	
	/**
	 * 鎶奙ap鍙傛暟杞崲涓篖ist
	 * @author mdc
	 * @date 2016骞�5鏈�12鏃�
	 * @param parameters
	 * @return
	 */
	private List<NameValuePair> convertNameValuePair(final Map<String, ?> parameters) {
		List<NameValuePair> values = new ArrayList<NameValuePair>(parameters.size());
		
		String value = null;
		for (Entry<String, ?> parameter : parameters.entrySet()) {
			value = parameter.getValue() == null? null : parameter.getValue().toString();
			values.add(new BasicNameValuePair(parameter.getKey(), value));
		}
		return values;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param file
	 * @return
	 */
	public HttpUtils setParameter(final File file) {
		if(builder != null) {
			builder.setFile(file);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param binary
	 * @return
	 */
	public HttpUtils setParameter(final byte[] binary) {
		if(builder != null) {
			builder.setBinary(binary);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param serializable
	 * @return
	 */
	public HttpUtils setParameter(final Serializable serializable) {
		if(builder != null) {
			builder.setSerializable(serializable);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 璁剧疆鍙傛暟涓篔son瀵硅薄
	 * @author mdc
	 * @date 2015骞�7鏈�27鏃�
	 * @param parameter 鍙傛暟瀵硅薄
	 * @return
	 */
	public HttpUtils setParameterJson(final Object parameter) {
		if(builder != null) {
			try {
				builder.setBinary(mapper.writeValueAsBytes(parameter));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param stream
	 * @return
	 */
	public HttpUtils setParameter(final InputStream stream) {
		if(builder != null) {
			builder.setStream(stream);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰鍙傛暟,浼氳鐩栦箣鍓嶇殑鍙傛暟
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param text
	 * @return
	 */
	public HttpUtils setParameter(final String text) {
		if(builder != null) {
			builder.setText(text);
		} else {
			uriBuilder.setParameters(URLEncodedUtils.parse(text, Consts.UTF_8));
		}
		return this;
	}
	
	
	/**
	 * 鍒涘缓涓�涓〃鍗�,娣诲姞璇锋眰鍙傛暟
	 * <div>妯℃嫙涓�涓祻瑙堝櫒琛ㄥ崟鐨勫舰寮忔彁浜よ姹�</div>
	 * <font color="red">鍒涘缓琛ㄥ崟鍚庝細涓㈠純涔嬪墠璁剧疆鐨勫��</font>
	 * @author mdc
	 * @date 2015骞�11鏈�6鏃�
	 * @return
	 */
	public FormEntity newForm() {
		partBuilder = MultipartEntityBuilder.create();
		return this.new FormEntity(this, partBuilder);
	}
	
	/**
	 * 琛ㄥ崟鍙傛暟鏋勫缓瀵硅薄
	 * @author mdc
	 * @date 2016骞�5鏈�11鏃�
	 */
	public class FormEntity {
		
		private MultipartEntityBuilder partBuilder;
		private HttpUtils httpUtils;
		
		public FormEntity(HttpUtils httpUtils, MultipartEntityBuilder partBuilder) {
			this.partBuilder = partBuilder;
			this.httpUtils = httpUtils;
			partBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param value 鍙傛暟鍊�
		 * @return
		 */
		public FormEntity addParameter(final String name, final String value) {
			partBuilder.addTextBody(name, value, ContentType.TEXT_PLAIN.withCharset("UTF-8"));
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param value 鍙傛暟鍊�
		 * @return
		 */
		public FormEntity addParameter(final String name, final Number value) {
			this.addParameter(name, value == null? null : value.toString());
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param parameters 鍙傛暟鍒楄〃
		 * @return
		 */
		public FormEntity addParameter(final NameValuePair ...parameters) {
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					partBuilder.addTextBody(parameters[i].getName(), parameters[i].getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
				}
		    }
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param value 鏂囦欢
		 * @return
		 */
		public FormEntity addParameter(final String name, final File file) {
			partBuilder.addPart(name, new FileBody(file));
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param bytes 鍙傛暟鍊�
		 * @return
		 */
		public FormEntity addParameter(final String name, final byte[] bytes) {
			partBuilder.addBinaryBody(name, bytes);
			return this;
		}

		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param stream 鍙傛暟鍊�
		 * @return
		 */
		public FormEntity addParameter(final String name, final InputStream stream) {
			partBuilder.addBinaryBody(name, stream);
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param bytes 鍙傛暟鍊�
		 * @param contentType 鏂囨。绫诲瀷
		 * @param filename 鏂囦欢鍚嶇О
		 * @return
		 */
		public FormEntity addParameter(final String name, final InputStream stream, final ContentType contentType, final String filename) {
			partBuilder.addBinaryBody(name, stream, contentType, filename);
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param bytes 鍙傛暟鍊�
		 * @param contentType 鏂囨。绫诲瀷
		 * @param filename 鏂囦欢鍚嶇О
		 * @return
		 */
		public FormEntity addParameter(final String name, final byte[] bytes, final ContentType contentType, final String filename) {
			partBuilder.addBinaryBody(name, bytes, contentType, filename);
			return this;
		}
		
		/**
		 * 娣诲姞鍙傛暟鍒板弬鏁板垪琛�
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @param name 鍙傛暟鍚嶇О
		 * @param file 鏂囦欢
		 * @param contentType 鏂囨。绫诲瀷
		 * @param filename 鏂囦欢鍚嶇О
		 * @return
		 */
		public FormEntity addParameter(final String name, final File file, final ContentType contentType, final String filename) {
			partBuilder.addBinaryBody(name, file, contentType, filename);
			return this;
		}
		
		/**
		 * 璁剧疆Boundary
		 * @author mdc
		 * @date 2015骞�12鏈�8鏃�
		 * @param boundary 
		 * @return
		 */
		public FormEntity setBoundary(String boundary){
			partBuilder.setBoundary(boundary);
			return this;
		}
		
		/**
		 * 璁剧疆璇锋眰鐨勫瓧绗﹂泦
		 * @author mdc
		 * @date 2016骞�5鏈�11鏃�
		 * @param charset 瀛楃闆�
		 * @return
		 */
		public FormEntity setCharset(final Charset charset){
			partBuilder.setCharset(charset);
			return this;
		}
		/**
		 * 鎵ц璇锋眰
		 * @author mdc
		 * @date 2015骞�11鏈�6鏃�
		 * @return
		 */
		public ResponseWrap execute() {
			return httpUtils.execute(0, partBuilder);
		}
		
		/**
		 * 鎵ц璇锋眰
		 * @author mdc
		 * @date 2015骞�7鏈�17鏃�
		 * @param responseHandler
		 * @return
		 */
		public <T> T execute(final ResponseHandler<? extends T> responseHandler) {
			return httpUtils.execute(responseHandler, partBuilder);
		}
	}
	
	/**
	 * 璁剧疆鍐呭缂栫爜
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param encoding
	 * @return
	 */
	public HttpUtils setContentEncoding(final String encoding) {
		if(builder != null) builder.setContentEncoding(encoding);
		return this;
	}
	
	/**
	 * 璁剧疆ContentType
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param contentType
	 * @return
	 */
	public HttpUtils setContentType(ContentType contentType) {
		if(builder != null) builder.setContentType(contentType);
		return this;
	}
	
	/**
	 * 璁剧疆ContentType
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param mimeType
	 * @param charset 鍐呭缂栫爜
	 * @return
	 */
	public HttpUtils setContentType(final String mimeType, final Charset charset) {
		if(builder != null) builder.setContentType(ContentType.create(mimeType, charset));
		return this;
	}
	
	/**
	 * 娣诲姞Header
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpUtils addHeader(String name, String value) {
		request.addHeader(name, value);
		return this;
	}
	
	/**
	 * 娣诲姞Header
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param headers
	 * @return
	 */
	public HttpUtils addHeaders(Map<String, String> headers) {
		for (Entry<String, String> header : headers.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}
		
		return this;
	}
	
	/**
	 * 璁剧疆Header,浼氳鐩栨墍鏈変箣鍓嶇殑Header
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param headers
	 * @return
	 */
	public HttpUtils setHeaders(Map<String, String> headers) {
		Header [] headerArray = new Header[headers.size()];
		int i = 0;
		
		for (Entry<String, String> header : headers.entrySet()) {
			headerArray[i++] = new BasicHeader(header.getKey(), header.getValue());
		}
		
		request.setHeaders(headerArray);
		return this;
	}
	
	public HttpUtils setHeaders(Header [] headers) {
		request.setHeaders(headers);
		return this;
	}
	
	public HttpUtils setHeader(Header header) {
		request.setHeader(header);
		return this;
	}
	
	public HttpUtils setHeader(String key, String name) {
		request.setHeader(new BasicHeader(key, name));
		return this;
	}
	
	/**
	 * 鑾峰彇鎵�鏈塇eader
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public Header[] getAllHeaders() {
		return request.getAllHeaders();
	}
	
	/**
	 * 绉婚櫎鎸囧畾name鐨凥eader鍒楄〃
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 */
	public HttpUtils removeHeaders(String name){
		request.removeHeaders(name);
		return this;
	}
	
	/**
	 * 绉婚櫎鎸囧畾鐨凥eader
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param header
	 */
	public HttpUtils removeHeader(Header header){
		request.removeHeader(header);
		return this;
	}
	
	/**
	 * 绉婚櫎鎸囧畾鐨凥eader
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 * @param value
	 */
	public HttpUtils removeHeader(String name, String value){
		request.removeHeader(new BasicHeader(name, value));
		return this;
	}
	
	/**
	 * 鏄惁瀛樺湪鎸囧畾name鐨凥eader
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param name
	 * @return
	 */
	public boolean containsHeader(String name){
		return request.containsHeader(name);
	}
	
	/**
	 * 鑾峰彇Header鐨勮凯浠ｅ櫒
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public HeaderIterator headerIterator(){
		return request.headerIterator();
	}
	
	/**
	 * 鑾峰彇鍗忚鐗堟湰淇℃伅
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public ProtocolVersion getProtocolVersion(){
		return request.getProtocolVersion();
	}
	
	/**
	 * 鑾峰彇璇锋眰Url
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public URI getURI(){
		return request.getURI();
	}
	
	/**
	 * 璁剧疆璇锋眰Url
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public HttpUtils setURI(URI uri){
		request.setURI(uri);
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰Url
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public HttpUtils setURI(String uri){
		return setURI(URI.create(uri));
	}
	
	/**
	 * 璁剧疆涓�涓狢ookieStore
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param cookieStore
	 * @return
	 */
	public HttpUtils SetCookieStore(CookieStore cookieStore){
		if(cookieStore == null) return this;
		this.cookieStore = cookieStore;
		return this;
	}
	
	/**
	 * 娣诲姞Cookie
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param cookie
	 * @return
	 */
	public HttpUtils addCookie(Cookie ...cookies){
		if(cookies == null) return this;
		
		for (int i = 0; i < cookies.length; i++) {
			cookieStore.addCookie(cookies[i]);
		}
		return this;
	}
	
	/**
	 * 璁剧疆缃戠粶浠ｇ悊
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param hostname
	 * @param port
	 * @return
	 */
	public HttpUtils setProxy(String hostname, int port) {
		HttpHost proxy = new HttpHost(hostname, port);
		return setProxy(proxy);
	}
	
	/**
	 * 璁剧疆缃戠粶浠ｇ悊
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param hostname
	 * @param port
	 * @param schema
	 * @return
	 */
	public HttpUtils setProxy(String hostname, int port, String schema) {
		HttpHost proxy = new HttpHost(hostname, port, schema);
		return setProxy(proxy);
	}
	
	/**
	 * 璁剧疆缃戠粶浠ｇ悊
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param address
	 * @return
	 */
	public HttpUtils setProxy(InetAddress address) {
		HttpHost proxy = new HttpHost(address);
		return setProxy(proxy);
	}
	
	/**
	 * 璁剧疆缃戠粶浠ｇ悊
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param host
	 * @return
	 */
	public HttpUtils setProxy(HttpHost host) {
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);
		clientBuilder.setRoutePlanner(routePlanner);
		return this;
	}
	
	/**
	 * 璁剧疆鍙屽悜璁よ瘉鐨凧KS
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param jksFilePath jks鏂囦欢璺緞
	 * @param password 瀵嗙爜
	 * @return
	 */
	public HttpUtils setJKS(String jksFilePath, String password) {
		return setJKS(new File(jksFilePath), password);
	}
	
	/**
	 * 璁剧疆鍙屽悜璁よ瘉鐨凧KS
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param jksFile jks鏂囦欢
	 * @param password 瀵嗙爜
	 * @return
	 */
	public HttpUtils setJKS(File jksFile, String password) {
		try (InputStream instream = new FileInputStream(jksFile)) {
			return setJKS(instream, password);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 璁剧疆鍙屽悜璁よ瘉鐨凧KS, 涓嶄細鍏抽棴InputStream
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param instream jks娴�
	 * @param password 瀵嗙爜
	 * @return
	 */
	public HttpUtils setJKS(InputStream instream, String password) {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
			keyStore.load(instream, password.toCharArray());
			return setJKS(keyStore, password);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 璁剧疆鍙屽悜璁よ瘉鐨凧KS
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param keyStore jks
	 * @param password 瀵嗙爜
	 * @return
	 */
	public HttpUtils setJKS(KeyStore keyStore, String password) {
		try {
			SSLContext sslContext = SSLContexts.custom().useTLS().loadKeyMaterial(keyStore, password.toCharArray())
					.loadTrustMaterial(keyStore).build();
			socketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return this;
	}
	
	/**
	 * 璁剧疆Socket瓒呮椂鏃堕棿,鍗曚綅:ms
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param socketTimeout
	 * @return
	 */
	public HttpUtils setSocketTimeout(int socketTimeout){
		config.setSocketTimeout(socketTimeout);
		return this;
	}
	
	/**
	 * 璁剧疆杩炴帴瓒呮椂鏃堕棿,鍗曚綅:ms
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param connectTimeout
	 * @return
	 */
	public HttpUtils setConnectTimeout(int connectTimeout) {
		config.setConnectTimeout(connectTimeout);
		return this;
	}
	
	/**
	 * 璁剧疆璇锋眰瓒呮椂鏃堕棿,鍗曚綅:ms
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param connectionRequestTimeout
	 * @return
	 */
	public HttpUtils setConnectionRequestTimeout(int connectionRequestTimeout) {
		config.setConnectionRequestTimeout(connectionRequestTimeout);
		return this;
	}
	
	/**
	 * 璁剧疆鏄惁鍏佽鏈嶅姟绔惊鐜噸瀹氬悜
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param circularRedirectsAllowed
	 * @return
	 */
	public HttpUtils setCircularRedirectsAllowed(boolean circularRedirectsAllowed) {
		config.setCircularRedirectsAllowed(circularRedirectsAllowed);
		return this;
	}
	
	/**
	 * 璁剧疆鏄惁鍚敤璋冭浆
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param redirectsEnabled
	 * @return
	 */
	public HttpUtils setRedirectsEnabled(boolean redirectsEnabled) {
		config.setRedirectsEnabled(redirectsEnabled);
		return this;
	}
	
	/**
	 * 璁剧疆閲嶅畾鍚戠殑娆℃暟
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 * @param maxRedirects
	 * @return
	 */
	public HttpUtils maxRedirects(int maxRedirects){
		config.setMaxRedirects(maxRedirects);
		return this;
	}
	
	/**
	 * 鎵ц璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	public ResponseWrap execute() {
		return execute(0, partBuilder);
	}
	
	/**
	 * 鎵ц璇锋眰
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @param responseHandler
	 * @return
	 */
	public <T> T execute(final ResponseHandler<? extends T> responseHandler) {
		return execute(responseHandler, partBuilder);
	}

	private ResponseWrap execute(int flag, MultipartEntityBuilder multiBuilder) {
		settingRequest(multiBuilder);
		if(httpClient == null) {
			httpClient = clientBuilder.build();
		}
		
		try {
			HttpClientContext context = HttpClientContext.create();
			CloseableHttpResponse response = httpClient.execute(request, context);
			return new ResponseWrap(httpClient, request, response, context, mapper);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private <T> T execute(final ResponseHandler<? extends T> responseHandler, MultipartEntityBuilder multiBuilder){
		settingRequest(multiBuilder);
		if(httpClient == null) httpClient = clientBuilder.build();
		
		try {
			return httpClient.execute(request, responseHandler);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 鍏抽棴杩炴帴
	 * @author mdc
	 * @date 2015骞�7鏈�18鏃�
	 */
	@SuppressWarnings("deprecation")
	public void shutdown(){
		httpClient.getConnectionManager().shutdown();
	}
	
	/**
	 * 鑾峰彇LayeredConnectionSocketFactory 浣跨敤ssl鍗曞悜璁よ瘉
	 * @author mdc
	 * @date 2015骞�7鏈�17鏃�
	 * @return
	 */
	private LayeredConnectionSocketFactory getSSLSocketFactory() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 淇′换鎵�鏈�
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return sslsf;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private void settingRequest(MultipartEntityBuilder multiBuilder) {
		
		URI uri = null;
		if(uriBuilder != null && uriBuilder.getQueryParams().size() != 0) {
			try {
				uri = uriBuilder.setPath(request.getURI().toString()).build();
			} catch (URISyntaxException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
		HttpEntity httpEntity = null;
		
		switch (type) {
		case 1:
			httpEntity = getEntityBuilder(multiBuilder);
			if(httpEntity.getContentLength() > 0) ((HttpPost)request).setEntity(httpEntity);
			break;
			
		case 2:
			HttpGet get = ((HttpGet)request);
			if (uri != null)  get.setURI(uri);
			break;
		
		case 3:
			httpEntity = getEntityBuilder(multiBuilder);
			if(httpEntity.getContentLength() > 0) ((HttpPut)request).setEntity(httpEntity);
			break;
			
		case 4:
			HttpDelete delete = ((HttpDelete)request);
			if (uri != null) delete.setURI(uri);
			
			break;
		}
		
		if (isHttps && socketFactory != null ) {
			clientBuilder.setSSLSocketFactory(socketFactory);
		
		} else if(isHttps) {
			clientBuilder.setSSLSocketFactory(getSSLSocketFactory());
		}
		
		clientBuilder.setDefaultCookieStore(cookieStore);
		request.setConfig(config.build());
	}

	/**
	 * @author mdc
	 * @date 2015骞�11鏈�9鏃�
	 * @param multiBuilder
	 * @return
	 */
	private HttpEntity getEntityBuilder(MultipartEntityBuilder multiBuilder) {
		HttpEntity httpEntity;
		if(multiBuilder != null) {
			httpEntity = multiBuilder.build();
		} else {
			httpEntity = builder.build();
		}
		return httpEntity;
	}
	
	//json杞崲鍣�
	public static ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.setSerializationInclusion(Include.NON_DEFAULT);
		// 璁剧疆杈撳叆鏃跺拷鐣ュ湪JSON瀛楃涓蹭腑瀛樺湪浣咼ava瀵硅薄瀹為檯娌℃湁鐨勫睘鎬�
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.getFactory().enable(Feature.ALLOW_COMMENTS);
        mapper.getFactory().enable(Feature.ALLOW_SINGLE_QUOTES);
	}
	
	public static void main(String[] args) throws IOException {
		//涓婁紶鏂囦欢
		Map<?, ?> s = HttpUtils.post("http://10.10.12.62:80/upload/")
		.setProxy("10.10.12.62", 80)
		.newForm().
		addParameter("file", new File("c:/a.png"))
		.execute().getJson(Map.class);
		
		//涓嬭浇鏂囦欢
		HttpUtils.get("http://10.10.12.62:80/download/" + s.get("fileid"))
		.execute().transferTo("c:/getImag.png");
	}
}