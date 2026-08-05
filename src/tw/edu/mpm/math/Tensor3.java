package tw.edu.mpm.math;

/**
 * ============================================================
 * MPM3D-Java
 *
 * Tensor3.java
 *
 * 三維二階張量類別
 *
 *
 * 用於：
 *
 *     Stress       σ
 *     Strain       ε
 *     Strain Rate  εdot
 *     Deformation Gradient F
 *
 *
 * Tensor形式：
 *
 *
 *       | Txx Txy Txz |
 *   T = | Tyx Tyy Tyz |
 *       | Tzx Tzy Tzz |
 *
 *
 * ============================================================
 */

public class Tensor3 {

	/*
	 * ========================================================
	 * 內部資料
	 *
	 * 使用3x3 double array
	 *
	 * ========================================================
	 */

	private double[][] data;

	/*
	 * ========================================================
	 * Constructor
	 * ========================================================
	 */

	/**
	 * 建立零張量
	 */
	public Tensor3() {
		data = new double[3][3];
	}

	/**
	 * 使用矩陣建立Tensor
	 */

	public Tensor3(double[][] values) {

		data = new double[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				data[i][j] = values[i][j];
			}
		}

	}

	/**
	 * 複製Constructor
	 */

	public Tensor3(Tensor3 other) {
		this(other.data);
	}

	/*
	 * ========================================================
	 * 元素存取
	 * ========================================================
	 */

	public double get(int i, int j) {
		return data[i][j];
	}

	public void set(int i, int j, double value) {
		data[i][j] = value;
	}

	/*
	 * ========================================================
	 * 基本運算
	 * ========================================================
	 */

	/**
	 * 張量加法
	 *
	 * A+B
	 */

	public Tensor3 add(Tensor3 B) {

		Tensor3 result = new Tensor3();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				result.data[i][j] = data[i][j] + B.data[i][j];
			}
		}

		return result;

	}

	/**
	 * 張量減法
	 *
	 * A-B
	 */

	public Tensor3 subtract(Tensor3 B) {

		Tensor3 result = new Tensor3();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				result.data[i][j] = data[i][j] - B.data[i][j];
			}
		}

		return result;

	}

	/**
	 * 純量乘法
	 *
	 * cA
	 */

	public Tensor3 multiply(double scalar) {

		Tensor3 result = new Tensor3();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				result.data[i][j] = data[i][j] * scalar;
			}
		}

		return result;

	}

	/*
	 * ========================================================
	 * Tensor基本運算
	 * ========================================================
	 */

	/**
	 *
	 * Trace
	 *
	 * tr(A)
	 *
	 */

	public double trace() {
		return data[0][0] + data[1][1] + data[2][2];
	}

	/**
	 *
	 * 轉置
	 *
	 * A^T
	 *
	 */

	public Tensor3 transpose() {

		Tensor3 result = new Tensor3();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				result.data[i][j] = data[j][i];
			}
		}

		return result;

	}

	/*
	 * ========================================================
	 * 對稱化
	 *
	 * 常用於Stress/Strain
	 * ========================================================
	 */

	/**
	 *
	 * sym(A)
	 *
	 * =1/2(A+A^T)
	 *
	 */

	public Tensor3 symmetric() {
		return this.add(this.transpose()).multiply(0.5);
	}

	/*
	 * ========================================================
	 * Deviatoric Tensor
	 *
	 * 偏差張量
	 *
	 * ========================================================
	 */

	/**
	 *
	 * S=A-1/3 tr(A)I
	 *
	 *
	 * 用於：
	 *
	 * von Mises stress
	 *
	 */

	public Tensor3 deviatoric() {

		Tensor3 result = new Tensor3(this);

		double mean = trace() / 3.0;
		result.data[0][0] -= mean;
		result.data[1][1] -= mean;
		result.data[2][2] -= mean;
		return result;

	}

	/*
	 * ========================================================
	 * Double Contraction
	 *
	 * A:B
	 *
	 * ========================================================
	 */

	/**
	 *
	 * Tensor內積
	 *
	 *
	 * A:B =
	 *
	 * Σ Aij Bij
	 *
	 */

	public double doubleDot(Tensor3 B) {

		double sum = 0.0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sum += data[i][j] * B.data[i][j];
			}
		}

		return sum;

	}

	/*
	 * ========================================================
	 * von Mises Stress
	 *
	 * ========================================================
	 */

	/**
	 *
	 * σvm
	 *
	 *
	 * =sqrt(3/2 S:S)
	 *
	 */

	public double vonMises() {

		Tensor3 s = deviatoric();

		return Math.sqrt(

				1.5 * s.doubleDot(s)

		);

	}

	/*
	 * ========================================================
	 * 轉換Matrix3
	 * ========================================================
	 */

	public Matrix3 toMatrix3() {
		return new Matrix3(data);
	}

	public static Tensor3 identity() {

		Tensor3 I = new Tensor3();
		I.data[0][0] = 1.0;
		I.data[1][1] = 1.0;
		I.data[2][2] = 1.0;

		return I;

	}

	public static Tensor3 zero() {
		return new Tensor3();
	}

	/**
	 * ============================================================
	 *
	 * Matrix3轉Tensor3
	 *
	 * ============================================================
	 */

	public static Tensor3 fromMatrix(Matrix3 m) {

		Tensor3 t = new Tensor3();

		t.data[0][0] = m.get(0, 0);
		t.data[0][1] = m.get(0, 1);
		t.data[0][2] = m.get(0, 2);

		t.data[1][0] = m.get(1, 0);
		t.data[1][1] = m.get(1, 1);
		t.data[1][2] = m.get(1, 2);

		t.data[2][0] = m.get(2, 0);
		t.data[2][1] = m.get(2, 1);
		t.data[2][2] = m.get(2, 2);

		return t;

	}

	/**
	*
	* stress += value I
	*
	*/

	public void addDiagonal(double value) {
		data[0][0] += value;
		data[1][1] += value;
		data[2][2] += value;
	}

	/*
	 * ========================================================
	 * 輸出
	 * ========================================================
	 */

	@Override

	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 3; i++) {

			sb.append("| ");

			for (int j = 0; j < 3; j++) {
				sb.append(String.format("%.6f ", data[i][j]));
			}

			sb.append("|\n");

		}

		return sb.toString();

	}

}