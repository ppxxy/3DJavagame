package game.connection.objects;

public abstract class WaiterObject<Type>{
	
	private final Class<Type> type;
	
	public WaiterObject(Class<Type> type){
		this.type = type;
	}
	
	public abstract boolean onReceive(Type object);
	
	@SuppressWarnings("unchecked")
	public boolean checkType(Object obj){
		boolean sameType = this.type.isAssignableFrom(obj.getClass());
		if(sameType){
			return onReceive((Type) obj);
		}
		return sameType;
	}
	
}
