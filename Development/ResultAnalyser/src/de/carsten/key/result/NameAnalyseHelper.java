package de.carsten.key.result;

public class NameAnalyseHelper {

	public static NameAnalyseHelperResult analyse(String name){
		String clazz = name.substring(0, name.indexOf("["));
		String method = name.substring(name.indexOf("::") + 2,
				name.indexOf("]"));
		String contractNumber = name.substring(name.lastIndexOf(".") + 1);
		
		String end = name.substring(name.lastIndexOf("].")+1);
		boolean lightweight = end.contains("JML behavior");
		boolean normal = end.contains("normal_behavior");
		boolean exceptional = end.contains("exceptional_behavior");
		
		String kind;
		if(lightweight)
//			kind = "Leichtgewichtig";
			kind = "L";
		else if(exceptional)
//			kind = "Exceptional";
			kind = "E";
		else if(normal)
//			kind = "Normal";
			kind = "N";
		else 
			kind = "?";
		return new NameAnalyseHelperResult(clazz, method, kind, Integer.parseInt(contractNumber));
	}
	
	public static class NameAnalyseHelperResult{
		private final String clazz;
		private final String method;
		private final String kind;
		private final int number;
		
		public NameAnalyseHelperResult(String clazz, String method,
				String kind, int number) {
			super();
			this.clazz = clazz;
			this.method = method;
			this.kind = kind;
			this.number = number;
		}

		public String getClazz() {
			return clazz;
		}
		public String getMethod() {
			return method;
		}
		public String getKind() {
			return kind;
		}
		public int getNumber() {
			return number;
		}
	}
}
