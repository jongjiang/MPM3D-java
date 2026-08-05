package tw.edu.mpm.math;

/**
 * ============================================================
 * MPM3D-Java
 *
 * Matrix3.java
 *
 * 三階方陣 (3 x 3 Matrix)
 *
 * 用於：
 *
 *     1. Deformation Gradient F
 *     2. Stress Tensor
 *     3. Strain Tensor
 *     4. Velocity Gradient
 *
 *
 * 矩陣形式：
 *
 *       | a00 a01 a02 |
 *   A = | a10 a11 a12 |
 *       | a20 a21 a22 |
 *
 *
 * ============================================================
 */

public class Matrix3 {

	/*
	 * ========================================================
	 * 矩陣資料
	 * ========================================================
	 */

	private double[][] m;

	/*
	 * ========================================================
	 * 建構子
	 * ========================================================
	 */

	/**
	 * 建立零矩陣
	 *
	 * [0]
	 */
	public Matrix3() {

		m = new double[3][3];

	}

	/**
	 * 建立指定矩陣
	 *
	 * @param data 3x3 array
	 */

	public Matrix3(double[][] data) {

		m = new double[3][3];

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				m[i][j] = data[i][j];

			}

		}

	}

	/**
	 * 複製矩陣
	 */

	public Matrix3(Matrix3 other) {

		this(other.m);

	}

	/*
	 * ========================================================
	 * 元素存取
	 * ========================================================
	 */

	public double get(int i, int j) {

		return m[i][j];

	}

	public void set(int i, int j, double value) {

		m[i][j] = value;

	}

	/*
	 * ========================================================
	 * 基本矩陣運算
	 * ========================================================
	 */

	/**
	 *
	 * A+B
	 *
	 */

	public Matrix3 add(Matrix3 B) {

		Matrix3 result = new Matrix3();

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				result.m[i][j] = this.m[i][j] + B.m[i][j];

			}

		}

		return result;

	}

	/**
	 *
	 * A-B
	 *
	 */

	public Matrix3 subtract(Matrix3 B) {

		Matrix3 result = new Matrix3();

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				result.m[i][j] = this.m[i][j] - B.m[i][j];

			}

		}

		return result;

	}

	/**
	 *
	 * A*c
	 *
	 */

	public Matrix3 multiply(double scalar) {

		Matrix3 result = new Matrix3();

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				result.m[i][j] = m[i][j] * scalar;

			}

		}

		return result;

	}

	/*
	 * ========================================================
	 * 矩陣乘法
	 * ========================================================
	 */

	/**
	 *
	 * C=A*B
	 *
	 */

	public Matrix3 multiply(Matrix3 B) {

		Matrix3 result = new Matrix3();

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				double sum = 0.0;

				for (int k = 0; k < 3; k++) {

					sum += this.m[i][k] * B.m[k][j];

				}

				result.m[i][j] = sum;

			}

		}

		return result;

	}

	/*
	 * ========================================================
	 * 矩陣與Vector乘法
	 * ========================================================
	 */

	/**
	 *
	 * y=A*x
	 *
	 */

	public Vector3 multiply(Vector3 v) {

		return new Vector3(

				m[0][0] * v.x + m[0][1] * v.y + m[0][2] * v.z,

				m[1][0] * v.x + m[1][1] * v.y + m[1][2] * v.z,

				m[2][0] * v.x + m[2][1] * v.y + m[2][2] * v.z

		);

	}

	/*
	 * ========================================================
	 * 轉置矩陣
	 * ========================================================
	 */

	/**
	 *
	 * A^T
	 *
	 */

	public Matrix3 transpose() {

		Matrix3 result = new Matrix3();

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				result.m[i][j] = m[j][i];

			}

		}

		return result;

	}

	/*
	 * ========================================================
	 * Determinant 行列式乘積
	 * ========================================================
	 */

	/**
	 *
	 * det(A)
	 *
	 */

	public double determinant() {

		return

		m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1])

				-

				m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0])

				+

				m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);

	}

	/*
	 * ========================================================
	 * 反矩陣
	 * ========================================================
	 */

	/**
	 *
	 * A^-1
	 *
	 */

	public Matrix3 inverse() {

		double det = determinant();

		if (Math.abs(det) < 1e-12) {

			throw new RuntimeException("Matrix不可逆");

		}

		Matrix3 result = new Matrix3();

		result.m[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[2][1]) / det;

		result.m[0][1] = (m[0][2] * m[2][1] - m[0][1] * m[2][2]) / det;

		result.m[0][2] = (m[0][1] * m[1][2] - m[0][2] * m[1][1]) / det;

		result.m[1][0] = (m[1][2] * m[2][0] - m[1][0] * m[2][2]) / det;

		result.m[1][1] = (m[0][0] * m[2][2] - m[0][2] * m[2][0]) / det;

		result.m[1][2] = (m[0][2] * m[1][0] - m[0][0] * m[1][2]) / det;

		result.m[2][0] = (m[1][0] * m[2][1] - m[1][1] * m[2][0]) / det;

		result.m[2][1] = (m[0][1] * m[2][0] - m[0][0] * m[2][1]) / det;

		result.m[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[1][0]) / det;

		return result;

	}

	/*
	 * ========================================================
	 * 常用矩陣
	 * ========================================================
	 */

	/**
	 * 單位矩陣
	 *
	 * I
	 */

	public static Matrix3 identity() {

		Matrix3 I = new Matrix3();

		I.m[0][0] = 1.0;

		I.m[1][1] = 1.0;

		I.m[2][2] = 1.0;

		return I;

	}

	/**
	 * 零矩陣
	 */

	public static Matrix3 zero() {

		return new Matrix3();

	}

	/**
	 * ============================================================
	 *
	 * Outer Product
	 *
	 * A += v ⊗ g
	 *
	 *
	 * 矩陣形式：
	 *
	 * |vx gx vx gy vx gz|
	 * |vy gx vy gy vy gz|
	 * |vz gx vz gy vz gz|
	 *
	 * ============================================================
	 */
	public void addOuterProduct(Vector3 v, Vector3 g) {

		m[0][0] += v.x * g.x;
		m[0][1] += v.x * g.y;
		m[0][2] += v.x * g.z;

		m[1][0] += v.y * g.x;
		m[1][1] += v.y * g.y;
		m[1][2] += v.y * g.z;

		m[2][0] += v.z * g.x;
		m[2][1] += v.z * g.y;
		m[2][2] += v.z * g.z;

	}

	/**
	 * ========================================================
	 * Trace
	 *
	 * tr(A)
	 *
	 * = A11 + A22 + A33
	 * ========================================================
	 */
	public double trace() {

	    return m[0][0]
	         + m[1][1]
	         + m[2][2];

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

				sb.append(String.format("%.5f ", m[i][j]));

			}

			sb.append("|\n");

		}

		return sb.toString();

	}

}