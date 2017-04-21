import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;	
import com.google.gson.JsonParser;

public class Http {

	public static String URL_LOGIN="http://222.200.98.147/login!doLogin.action";
	public static String URL_GETCLASS_LIST="http://222.200.98.147/xsxklist!getDataList.action";
	public static String URL_SELECTCLASS="http://222.200.98.147/xsxklist!getAdd.action";
	public static String URL_GETOWN_CLASS="http://222.200.98.147/xsxklist!getXzkcList.action";
	
	
	public static CloseableHttpClient client=HttpClients.createDefault();
	
	

	public static String post(String url,ArrayList<BasicNameValuePair> list) throws Exception {
		HttpPost post=new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response=client.execute(post);
			post.abort();
			//System.out.println("返回内容:  "+EntityUtils.toString(response.getEntity()));
			String data=EntityUtils.toString(response.getEntity());
			
			if(response.getStatusLine().getStatusCode()==200){
				return data;
			}else{
				return null;
			}
	}
	
	public  static boolean loginIn(String num,String pwd){
		ArrayList<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("account",num));
		list.add(new BasicNameValuePair("pwd", pwd));
		list.add(new BasicNameValuePair("verifycode", ""));
		
		String data = null;
		try {
			data = post(URL_LOGIN, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonParser parser=new JsonParser();
		JsonObject object=(JsonObject) parser.parse(data);
		
		if("y".equals(object.get("status").getAsString())){
			System.out.println("登录成功");
			//getClassList();
			return true;
		}
		else{
			System.out.println("登录失败");
			return false;
			
		}
	}


	public  static Vector<Class> getClassList(String page) {
		Vector<Class> vector=new Vector<>();
		
		ArrayList<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("page", page));
		list.add(new BasicNameValuePair("rows", "150"));
		list.add(new BasicNameValuePair("sort", "kcrwdm"));
		list.add(new BasicNameValuePair("order", "asc"));
		String data = null;
	
		try {
			data = post(URL_GETCLASS_LIST, list);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JsonParser parser=new JsonParser();
		JsonObject object=(JsonObject) parser.parse(data);
		JsonArray rows=object.get("rows").getAsJsonArray();
		
		for(int i=0;i<rows.size();i++){
			JsonObject rowsObject=rows.get(i).getAsJsonObject();
			String classNum=rowsObject.get("kcrwdm").getAsString();
            String className=rowsObject.get("kcmc").getAsString();
            String classTime=rowsObject.get("xmmc").getAsString();
            String classTeacher=rowsObject.get("teaxm").getAsString();
        	String classPeople=rowsObject.get("pkrs").getAsString();
        	String classType=rowsObject.get("kcflmc").getAsString();
        	String classSelectPeople=rowsObject.get("jxbrs").getAsString();
            
//			System.out.println("课程代码: "+classNum); 
//			System.out.println("课程名称: "+className);
			vector.addElement(new Class(classNum,className,classTime,classTeacher,classType,classPeople,classSelectPeople));
		}
		
		setPage(object);
		
		return  vector;
	}

	public static void setPage(JsonObject object) {
		//设置多少页
		String total=object.get("total").getAsString();
		MainPanel.totalPage=Integer.valueOf(total)/150+1;
	}
	
	
	public static String selectClass(String classNum,String className){
		ArrayList<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("kcrwdm", classNum));
		String classNameEncoder="";
		try {
		classNameEncoder=URLEncoder.encode(className,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		list.add(new BasicNameValuePair("kcmc", classNameEncoder));
		String data = null;
		try {
			data = post(URL_SELECTCLASS, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data);
		return data;
	}
	
	
	
	
	
	public static Vector<Class> getOwnClass(){
		Vector<Class> vector=new Vector<>();
		ArrayList<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("sort", "kcrwdm"));
		list.add(new BasicNameValuePair("order", "asc"));
		String data="";
		try {
			data=post(URL_GETOWN_CLASS, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonParser parser=new JsonParser();
		JsonArray rows=(JsonArray) parser.parse(data);
		
		for(int i=0;i<rows.size();i++){
			JsonObject rowsObject=rows.get(i).getAsJsonObject();
			String classNum=rowsObject.get("kcrwdm").getAsString();
            String className=rowsObject.get("kcmc").getAsString();
            String classTime=rowsObject.get("xmmc").getAsString();
            String classTeacher=rowsObject.get("teaxm").getAsString();
        	String classPeople=rowsObject.get("pkrs").getAsString();
        	String classType=rowsObject.get("kcflmc").getAsString();
        	String classSelectPeople=rowsObject.get("jxbrs").getAsString();
            
//			System.out.println("课程代码: "+classNum); 
//			System.out.println("课程名称: "+className);
			vector.addElement(new Class(classNum,className,classTime,classTeacher,classType,classPeople,classSelectPeople));
		}
		
		return vector;
	}
	
	
	public static Vector<Class> searchClass(String searchValue){
		Vector<Class> vector=new Vector<>();
		
		try {
			searchValue=URLEncoder.encode(searchValue,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(searchValue);
		ArrayList<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("searchKey", "kcmc"));
		list.add(new BasicNameValuePair("searchValue", searchValue));
		list.add(new BasicNameValuePair("page", "1"));
		list.add(new BasicNameValuePair("rows", "50"));
		list.add(new BasicNameValuePair("sort", "kcrwdm"));
		list.add(new BasicNameValuePair("order", "asc"));
		String data="";
		try {
			data=post(URL_GETCLASS_LIST, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonParser parser=new JsonParser();
		JsonObject object=(JsonObject) parser.parse(data);
		JsonArray rows=object.get("rows").getAsJsonArray();
		
		for(int i=0;i<rows.size();i++){
			JsonObject rowsObject=rows.get(i).getAsJsonObject();
			String classNum=rowsObject.get("kcrwdm").getAsString();
            String className=rowsObject.get("kcmc").getAsString();
            String classTime=rowsObject.get("xmmc").getAsString();
            String classTeacher=rowsObject.get("teaxm").getAsString();
        	String classPeople=rowsObject.get("pkrs").getAsString();
        	String classType=rowsObject.get("kcflmc").getAsString();
        	String classSelectPeople=rowsObject.get("jxbrs").getAsString();
            
//			System.out.println("课程代码: "+classNum); 
//			System.out.println("课程名称: "+className);
			vector.addElement(new Class(classNum,className,classTime,classTeacher,classType,classPeople,classSelectPeople));
		}
		
		setPage(object);
		return vector;
	}
}
































