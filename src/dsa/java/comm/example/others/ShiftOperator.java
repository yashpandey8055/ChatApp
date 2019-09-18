package comm.example.others;



public class ShiftOperator {


	public static void main(String[] args) {
		long number = 10;
		
		for(int i=1;i<32;i++) {
			System.out.println("Shift By "+ i +" place produces :: "+(number>>i));
		}
		System.out.println("Shift By some place produces :: "+(2147483647+1));
		
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
