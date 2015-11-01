package JImageStats;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageDatabase extends Database {
	public static String CreateImageTableSQL="CREATE MEMORY TABLE IMAGE ( "
											+"FILEPATH VARCHAR(512) PRIMARY KEY, "
											+"FILENAME VARCHAR(512), "
											+"ISO INTEGER, "
											+"APERTURE DOUBLE, "
											+"SHUTTER_SPEED VARCHAR(20), "
											+"FOCAL_LENGTH DOUBLE, "
											+"CREATION_DATE VARCHAR(30), "
											+"CAMERA_MODEL VARCHAR(100), "
											+"CAMERA_LENS VARCHAR(100), "
											+"FOREIGN KEY (CAMERA_MODEL) REFERENCES CAMERA (MODEL), "
											+"FOREIGN KEY (CAMERA_LENS) REFERENCES LENS (MODEL)"
											+")";
	public static String CreateCameraTableSQL="CREATE MEMORY TABLE CAMERA ( "
											 +"MODEL VARCHAR(100) PRIMARY KEY"
											 +")";
	public static String CreateLensTableSQL ="CREATE MEMORY TABLE LENS ( "
											+"MODEL VARCHAR(100) PRIMARY KEY"
											+")";
	public static String AllKeyword="-All-";
	public static String CreateGenericCameraSQL="INSERT INTO CAMERA (MODEL) VALUES ('"+AllKeyword+"')";
	public static String CreateGenericLensSQL="INSERT INTO LENS (MODEL) VALUES ('"+AllKeyword+"')";
	public static String QueryCameraSQL="SELECT COUNT(MODEL) NO FROM CAMERA WHERE MODEL=";
	public static String QueryLensSQL="SELECT COUNT(MODEL) NO FROM LENS WHERE MODEL=";
	
	public ImageDatabase () {
		super();
		executeSQL(CreateCameraTableSQL);
		executeSQL(CreateLensTableSQL);
		executeSQL(CreateImageTableSQL);
		executeSQL(CreateGenericCameraSQL);
		executeSQL(CreateGenericLensSQL);
	}
	
	public void registerImage (ImageInfo info) {
		this.registerBody(info.body);
		this.registerLens(info.lens);
		executeSQL("INSERT INTO IMAGE (FILEPATH, FILENAME, ISO, APERTURE, SHUTTER_SPEED, FOCAL_LENGTH, CREATION_DATE, CAMERA_MODEL, CAMERA_LENS)"
				+ " VALUES ('"+info.filepath+"', '"+info.filename+"', "+info.ISO+", "+info.aperture+", '"+info.shutterSpeed+"', "+info.focalLength+", '"+info.date+"', '"+info.body+"', '"+info.lens+"')");
	}
	
	public void registerBody (String model) {
		ResultSet rs=executeSQL(QueryCameraSQL+"'"+model+"'");
		try {
			rs.next();
			if (rs.getInt(1)==0) {
				executeSQL("INSERT INTO CAMERA (MODEL) VALUES ('"+model+"')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerLens (String model) {
		ResultSet rs=executeSQL(QueryLensSQL+"'"+model+"'");
		try {
			rs.next();
			if (rs.getInt(1)==0) {
				executeSQL("INSERT INTO LENS (MODEL) VALUES ('"+model+"')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ImageInfo [] queryImageInfo (String model, String lens) {
		String sqlStatement="SELECT * FROM IMAGE";
		if (!model.equals(AllKeyword) && lens.equals(AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"'";
		} else if (model.equals(AllKeyword) && !lens.equals(AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_LENS='"+lens+"'";
		} else if (!model.equals(AllKeyword) && !lens.equals(AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"' AND CAMERA_LENS='"+lens+"'";
		}
		sqlStatement=sqlStatement+" ORDER BY FILEPATH ASC";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<ImageInfo> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				ImageInfo info=new ImageInfo();
				info.filepath=rs.getString(1);
				info.filename=rs.getString(2);
				info.ISO=rs.getInt(3);
				info.aperture=rs.getDouble(4);
				info.shutterSpeed=rs.getString(5);
				info.focalLength=rs.getDouble(6);
				info.date=rs.getString(7);
				info.body=rs.getString(8);
				info.lens=rs.getString(9);
				
				infoList.add(info);
			}
			ImageInfo [] infoAry=infoList.toArray(new ImageInfo [infoList.size()]);
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String [] cameraInfo () {
		String sqlStatement="SELECT * FROM CAMERA";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<String> cameraList=new ArrayList<>();
		try {
			while (rs.next()) {
				cameraList.add(rs.getString(1));
			}
			String [] cameraAry=cameraList.toArray(new String [cameraList.size()]);
			return cameraAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String [] lensInfo () {
		String sqlStatement="SELECT * FROM LENS";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<String> lensList=new ArrayList<>();
		try {
			while (rs.next()) {
				lensList.add(rs.getString(1));
			}
			String [] lensAry=lensList.toArray(new String [lensList.size()]);
			return lensAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String [][] genericResultExtraction (String sqlStatement) {
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<String []> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				infoList.add(new String [] {rs.getString(1), rs.getString(2)});
			}
			String [][] infoAry=infoList.toArray(new String [infoList.size()][2]);
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String [][] queryBodyUsage () {
		String sqlStatement="SELECT CAMERA_MODEL, COUNT(CAMERA_MODEL) FROM IMAGE GROUP BY CAMERA_MODEL";
		return this.genericResultExtraction(sqlStatement);
	}
	
	public String [][] queryLensUsage (String body) {
		String sqlStatement="SELECT CAMERA_LENS, COUNT(CAMERA_LENS) FROM IMAGE";
		if (!body.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+body+"'";
		}
		sqlStatement=sqlStatement+" GROUP BY CAMERA_LENS";
		return this.genericResultExtraction(sqlStatement);
	}
	
	public Integer [][] queryISOUsage (String model, String lens) {
		String sqlStatement="SELECT ISO, COUNT(ISO) FROM IMAGE";
		if (!model.equals(ImageDatabase.AllKeyword) && lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"'";
		} else if (model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_LENS='"+lens+"'";
		} else if (!model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"' AND CAMERA_LENS='"+lens+"'";
		}
		sqlStatement=sqlStatement+" GROUP BY ISO ORDER BY ISO ASC";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<Integer []> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				infoList.add(new Integer [] {rs.getInt(1), rs.getInt(2)});
			}
			Integer [][] infoAry=infoList.toArray(new Integer [infoList.size()][2]);
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static class ShutterSpeedInfo implements Comparable<ShutterSpeedInfo> {
		private static MathContext bigDecimalPrecision=new MathContext(10);
		public BigDecimal shutterSpeed;
		public String shutterSpeedStr;
		public String count;
		
		public ShutterSpeedInfo (String speed, int c) {
			this.shutterSpeedStr=speed;
			this.count=String.valueOf(c);
			
			if (!this.shutterSpeedStr.equals("")) {
				if (this.shutterSpeedStr.contains("/")) {
					String [] s=this.shutterSpeedStr.split("/");
					this.shutterSpeed=new BigDecimal(s[0]).divide(new BigDecimal(s[1]),bigDecimalPrecision);
				} else {
					this.shutterSpeed=new BigDecimal(this.shutterSpeedStr);
				}
			} else {
				this.shutterSpeed=BigDecimal.ZERO;
			}
		}
		
		public int compareTo(ShutterSpeedInfo ssi) {
			return this.shutterSpeed.compareTo(ssi.shutterSpeed);
		}
	}
	
	public String [][] queryShutterSpeedUsage (String model, String lens) {
		String sqlStatement="SELECT SHUTTER_SPEED, COUNT(SHUTTER_SPEED) FROM IMAGE";
		if (!model.equals(ImageDatabase.AllKeyword) && lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"'";
		} else if (model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_LENS='"+lens+"'";
		} else if (!model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"' AND CAMERA_LENS='"+lens+"'";
		}
		sqlStatement=sqlStatement+" GROUP BY SHUTTER_SPEED";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<ShutterSpeedInfo> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				infoList.add(new ShutterSpeedInfo(rs.getString(1), rs.getInt(2)));
			}
			ShutterSpeedInfo [] ssiAry=infoList.toArray(new ShutterSpeedInfo [infoList.size()]);
			Arrays.sort(ssiAry);
			String [][] infoAry=new String [ssiAry.length][2];
			for (int i=0;i<ssiAry.length;i++) {
				infoAry[i][0]=ssiAry[i].shutterSpeedStr;
				infoAry[i][1]=ssiAry[i].count;
			}
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String [][] queryFocalLengthUsage (String model, String lens) {
		String sqlStatement="SELECT FOCAL_LENGTH, COUNT(FOCAL_LENGTH) FROM IMAGE";
		if (!model.equals(ImageDatabase.AllKeyword) && lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"'";
		} else if (model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_LENS='"+lens+"'";
		} else if (!model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"' AND CAMERA_LENS='"+lens+"'";
		}
		sqlStatement=sqlStatement+" GROUP BY FOCAL_LENGTH ORDER BY FOCAL_LENGTH ASC";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<String []> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				infoList.add(new String [] {(int)rs.getDouble(1)+"", rs.getInt(2)+""});
			}
			String [][] infoAry=infoList.toArray(new String [infoList.size()][2]);
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String [][] queryApertureUsage (String model, String lens) {
		String sqlStatement="SELECT APERTURE, COUNT(APERTURE) FROM IMAGE";
		if (!model.equals(ImageDatabase.AllKeyword) && lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"'";
		} else if (model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_LENS='"+lens+"'";
		} else if (!model.equals(ImageDatabase.AllKeyword) && !lens.equals(ImageDatabase.AllKeyword)) {
			sqlStatement=sqlStatement+" WHERE CAMERA_MODEL='"+model+"' AND CAMERA_LENS='"+lens+"'";
		}
		sqlStatement=sqlStatement+" GROUP BY APERTURE ORDER BY APERTURE ASC";
		ResultSet rs=executeSQL(sqlStatement);
		ArrayList<String []> infoList=new ArrayList<>();
		try {
			while (rs.next()) {
				infoList.add(new String [] {rs.getDouble(1)+"", rs.getInt(2)+""});
			}
			String [][] infoAry=infoList.toArray(new String [infoList.size()][2]);
			return infoAry;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
