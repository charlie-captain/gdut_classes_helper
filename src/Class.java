
public class Class {

	
	private String className;
	private String classNum;
	private String classTime;
	private String classTeacher;
	private String classPeople;
	private String classType;
	private String classSelectPeople;
	
	
	
	
	public Class(String classNum, String className, String classTime,String classTeacher,String classType,String classPeople,String classSelectPeople) {
		this.classNum=classNum;
		this.className=className;
		this.setClassTime(classTime);
		this.setClassTeacher(classTeacher);
		this.setClassPeople(classPeople);
		this.setClassType(classType);
		this.setClassSelectPeople(classSelectPeople);
	}

	/**
	 * @return the classNum
	 */
	public String getClassNum() {
		return classNum;
	}
	/**
	 * @param classNum the classNum to set
	 */
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	/**
	 * @return the classTeacher
	 */
	public String getClassTeacher() {
		return classTeacher;
	}

	/**
	 * @param classTeacher the classTeacher to set
	 */
	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}

	/**
	 * @return the classPeople
	 */
	public String getClassPeople() {
		return classPeople;
	}

	/**
	 * @param classPeople the classPeople to set
	 */
	public void setClassPeople(String classPeople) {
		this.classPeople = classPeople;
	}

	/**
	 * @return the classType
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * @param classType the classType to set
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @return the classSelectPeople
	 */
	public String getClassSelectPeople() {
		return classSelectPeople;
	}

	/**
	 * @param classSelectPeople the classSelectPeople to set
	 */
	public void setClassSelectPeople(String classSelectPeople) {
		this.classSelectPeople = classSelectPeople;
	}
}
