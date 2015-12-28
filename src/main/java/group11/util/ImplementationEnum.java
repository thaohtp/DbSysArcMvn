package group11.util;

public enum ImplementationEnum {
		
	SYSTEM_CALL(1), BUFFERED(2), BUFFERED_WITH_SIZE(3), MAPPING(4);
	private Integer implementationNumber;
	
	ImplementationEnum(Integer implNumber){
		this.implementationNumber = implNumber;
	}
	
	public Integer getImplementationNumber(){
		return this.implementationNumber;
	}
	
	public static ImplementationEnum getImplementationEnum(Integer implNumber){
		switch (implNumber) {
		case 1:
			return SYSTEM_CALL;
		case 2:
			return BUFFERED;
		case 3:
			return BUFFERED_WITH_SIZE;
		case 4: 
			return MAPPING;
		default:
			return SYSTEM_CALL;
		}
	}

}
