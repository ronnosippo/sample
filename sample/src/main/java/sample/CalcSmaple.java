package sample;

public class CalcSmaple {


	public static void main(String...args) {

		double d = 0.01;
		double sum =0.0;

		for(int i=0;i<10000;i++) {
			sum += d;
		}
		System.out.println(sum);
	}

}
