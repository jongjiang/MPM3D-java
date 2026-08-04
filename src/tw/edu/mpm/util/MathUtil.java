package tw.edu.mpm.util;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 * MPM3D-Java
 *
 * MathUtil.java
 *
 * 數學工具類別
 *
 *
 * 功能：
 *
 *     1. 數值計算
 *     2. 插值
 *     3. CFL時間步長
 *     4. 向量工具
 *     5. 誤差判斷
 *
 *
 * ============================================================
 */

public final class MathUtil {

	/*
	 * ========================================================
	 * 防止建立物件
	 * ========================================================
	 */

	private MathUtil() {

	}

	/*
	 * ========================================================
	 * 基本數值工具
	 * ========================================================
	 */

	/**
	 *
	 * 限制數值範圍
	 *
	 *
	 * 若：
	 *
	 * value < min
	 *
	 * 回傳 min
	 *
	 *
	 * value > max
	 *
	 * 回傳 max
	 *
	 */

	public static double clamp(double value, double min, double max) {

		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;

	}

	/**
	 *
	 * 判斷兩數是否接近
	 *
	 */

	public static boolean almostEqual(double a, double b) {
		return Math.abs(a - b) < 1e-12;
	}

	/**
	 *
	 * 最大值
	 *
	 */

	public static double max(double a, double b) {
		return a > b ? a : b;
	}

	/**
	 *
	 * 最小值
	 *
	 */

	public static double min(double a, double b) {
		return a < b ? a : b;
	}

	/*
	 * ========================================================
	 * 插值
	 * ========================================================
	 */

	/**
	 *
	 * Linear Interpolation
	 *
	 *
	 * y = (1-t)a + t*b
	 *
	 */

	public static double lerp(double a, double b, double t) {
		return a + (b - a) * t;
	}

	/**
	 *
	 * 三維向量插值
	 *
	 */

	public static Vector3 lerp(Vector3 a, Vector3 b, double t) {

		return new Vector3(
				lerp(a.x, b.x, t),
				lerp(a.y, b.y, t),
				lerp(a.z, b.z, t)
		);

	}

	/*
	 * ========================================================
	 * 角度轉換
	 * ========================================================
	 */

	/**
	 * Degree → Radian
	 */

	public static double toRadians(double degree) {

		return degree * Math.PI / 180.0;

	}

	/**
	 * Radian → Degree
	 */

	public static double toDegree(double radian) {

		return radian * 180.0 / Math.PI;

	}

	/*
	 * ========================================================
	 * Vector工具
	 * ========================================================
	 */

	/**
	 *
	 * 計算向量長度
	 *
	 */

	public static double norm(Vector3 v) {

		return Math.sqrt(
				v.x * v.x + v.y * v.y + v.z * v.z
		);

	}

	/**
	 *
	 * Vector最大分量
	 *
	 */

	public static double maxComponent(Vector3 v) {

		return Math.max(

				Math.max(Math.abs(v.x), Math.abs(v.y)),

				Math.abs(v.z)

		);

	}

	/*
	 * ========================================================
	 * MPM相關計算
	 * ========================================================
	 */

	/**
	 *
	 * CFL穩定時間步長
	 *
	 *
	 * Explicit MPM:
	 *
	 *
	 * Δt < CFL * dx / c
	 *
	 *
	 * c = sqrt(E/rho)
	 *
	 *
	 * @param dx Grid尺寸
	 *
	 * @param young Young modulus
	 *
	 * @param density 密度
	 *
	 */

	public static double computeCFLTimeStep(double dx, double young, double density) {

		/*
		 * Elastic wave speed
		 *
		 * c = sqrt(E/rho)
		 *
		 */

		double waveSpeed = Math.sqrt(young / density);

		return Constants.CFL_FACTOR * dx / waveSpeed;

	}

	/**
	 *
	 * 計算Lamé第一參數
	 *
	 *
	 * λ = Eν / ((1+ν)(1-2ν))
	 * 
	 *
	 */

	public static double lameLambda(double E, double nu) {
		return E * nu / ((1 + nu) * (1 - 2 * nu));
	}

	/**
	 *
	 * Shear modulus
	 *
	 *
	 * μ = E/(2(1+ν))
	 * 
	 *
	 */

	public static double shearModulus(double E, double nu) {
		return E / (2 * (1 + nu));
	}

	/*
	 * ========================================================
	 * Particle相關工具
	 * ========================================================
	 */

	/**
	 *
	 * 計算Particle體積
	 *
	 *
	 * V = m/rho
	 *
	 */

	public static double particleVolume(double mass, double density) {
		return mass / density;
	}

	/**
	 *
	 * 判斷點是否在範圍內
	 *
	 */

	public static boolean insideBox(Vector3 p, Vector3 min, Vector3 max) {
		return  p.x >= min.x && p.x <= max.x &&
				p.y >= min.y && p.y <= max.y &&
				p.z >= min.z && p.z <= max.z;
	}

	/*
	 * ========================================================
	 * 隨機數
	 * ========================================================
	 */

	/**
	 *
	 * 產生[0,1]亂數
	 *
	 */

	public static double random() {
		return Math.random();
	}

	/**
	 *
	 * 產生範圍亂數
	 *
	 */

	public static double random(double min, double max) {
		return min + Math.random() * (max - min);
	}

}