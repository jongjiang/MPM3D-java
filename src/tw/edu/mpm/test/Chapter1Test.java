package tw.edu.mpm.test;

import tw.edu.mpm.math.Vector3;
import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.util.Constants;
import tw.edu.mpm.util.MathUtil;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * Chapter1Test.java
 *
 * 第一章完整測試程式
 *
 *
 * 測試內容：
 *
 *     1. Vector3
 *     2. Matrix3
 *     3. Tensor3
 *     4. MathUtil
 *     5. Constants
 *
 *
 * ============================================================
 */

public class Chapter1Test {

	public static void main(String[] args) {

		System.out.println("====================================");

		System.out.println(" MPM3D-Java Chapter 1 Test");

		System.out.println("====================================");

		/*
		 * ====================================================
		 *
		 * Test 1
		 *
		 * Vector3
		 *
		 * ====================================================
		 */

		testVector3();

		/*
		 * ====================================================
		 *
		 * Test 2
		 *
		 * Matrix3
		 *
		 * ====================================================
		 */

		testMatrix3();

		/*
		 * ====================================================
		 *
		 * Test 3
		 *
		 * Tensor3
		 *
		 * ====================================================
		 */

		testTensor3();

		/*
		 * ====================================================
		 *
		 * Test 4
		 *
		 * MathUtil
		 *
		 * ====================================================
		 */

		testMathUtil();

		/*
		 * ====================================================
		 *
		 * Test 5
		 *
		 * Constants
		 *
		 * ====================================================
		 */

		testConstants();

		System.out.println("====================================");

		System.out.println(" Chapter 1 Test Finished");

		System.out.println("====================================");

	}

	/**
	 * ========================================================
	 *
	 * Vector3測試
	 *
	 * ========================================================
	 */

	private static void testVector3() {

		System.out.println("\n[ Test Vector3 ]");

		Vector3 a = new Vector3(1, 2, 3);

		Vector3 b = new Vector3(4, 5, 6);

		System.out.println("a = " + a);

		System.out.println("b = " + b);

		System.out.println("a+b = " + a.add(b));

		System.out.println("a-b = " + a.subtract(b));

		System.out.println("a dot b = " + a.dot(b));

		System.out.println("a cross b = " + a.cross(b));

		System.out.println("|a| = " + a.magnitude());

		System.out.println("normalize(a)= " + a.normalize());

	}

	/**
	 * ========================================================
	 *
	 * Matrix3測試
	 *
	 * ========================================================
	 */

	private static void testMatrix3() {

		System.out.println("\n[ Test Matrix3 ]");

		Matrix3 A = new Matrix3();

		A.set(0, 0, 1);

		A.set(1, 1, 2);

		A.set(2, 2, 3);

		System.out.println("A=");

		System.out.println(A);

		System.out.println("det(A)=" + A.determinant());

		System.out.println("A inverse=");

		System.out.println(A.inverse());

		Matrix3 I = Matrix3.identityMatrix();

		System.out.println("Identity=");

		System.out.println(I);

		Vector3 v = new Vector3(1, 2, 3);

		System.out.println("A*v=" + A.multiply(v));

	}

	/**
	 * ========================================================
	 *
	 * Tensor3測試
	 *
	 * ========================================================
	 */

	private static void testTensor3() {

		System.out.println("\n[ Test Tensor3 ]");

		Tensor3 stress = new Tensor3();

		/*
		 *
		 * 建立假想應力
		 *
		 */

		stress.set(0, 0, 100);

		stress.set(1, 1, 50);

		stress.set(2, 2, 25);

		System.out.println("Stress Tensor=");

		System.out.println(stress);

		System.out.println("Trace=" + stress.trace());

		System.out.println("Deviatoric=");

		System.out.println(stress.deviatoric());

		System.out.println("von Mises=" + stress.vonMises());

	}

	/**
	 * ========================================================
	 *
	 * MathUtil測試
	 *
	 * ========================================================
	 */

	private static void testMathUtil() {

		System.out.println("\n[ Test MathUtil ]");

		double value = 15;

		System.out.println("Clamp=" + MathUtil.clamp(value, 0, 10));

		System.out.println("lerp=" + MathUtil.lerp(0, 10, 0.5));

		double dt = MathUtil.computeCFLTimeStep(Constants.GRID_SIZE, Constants.YOUNG_MODULUS, Constants.DENSITY);

		System.out.println("CFL dt=" + dt);

		System.out.println("Lambda=" + MathUtil.lameLambda(Constants.YOUNG_MODULUS, Constants.POISSON_RATIO));

	}

	/**
	 * ========================================================
	 *
	 * Constants測試
	 *
	 * ========================================================
	 */

	private static void testConstants() {

		System.out.println("\n[ Test Constants ]");

		System.out.println("Dimension=" + Constants.DIMENSION);

		System.out.println("Grid Size=" + Constants.GRID_SIZE);

		System.out.println("Density=" + Constants.DENSITY);

		System.out.println("Young Modulus=" + Constants.YOUNG_MODULUS);

	}

}