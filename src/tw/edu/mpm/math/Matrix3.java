package tw.edu.mpm.math;

/**
 * ============================================================
 * MPM3D-Java
 *
 * Matrix3.java
 *
 * 三維 3×3 矩陣
 *
 * 高效能版本：
 *
 * 1. 使用 9 個 double 儲存
 * 2. 避免 double[][]
 * 3. 避免 Array Index
 * 4. 適合 MPM/FEM 大量運算
 *
 * ------------------------------------------------------------
 *
 *      | xx  xy  xz |
 *  A = | yx  yy  yz |
 *      | zx  zy  zz |
 *
 * ============================================================
 */
public class Matrix3 {

	/*
	 * ========================================================
	 * Matrix Elements
	 * ========================================================
	 */

	public double xx;
	public double xy;
	public double xz;

	public double yx;
	public double yy;
	public double yz;

	public double zx;
	public double zy;
	public double zz;

	/*
	 * ========================================================
	 * Constructors
	 * ========================================================
	 */

	/**
	 * 建立零矩陣
	 */
	public Matrix3() {
	}

	/**
	 * 建立指定矩陣
	 */
	public Matrix3(double xx, double xy, double xz, double yx, double yy, double yz, double zx, double zy, double zz) {

		this.xx = xx;
		this.xy = xy;
		this.xz = xz;

		this.yx = yx;
		this.yy = yy;
		this.yz = yz;

		this.zx = zx;
		this.zy = zy;
		this.zz = zz;
	}

	/**
	 * 使用 double[3][3] 建立矩陣
	 */
	public Matrix3(double[][] m) {

		xx = m[0][0];
		xy = m[0][1];
		xz = m[0][2];

		yx = m[1][0];
		yy = m[1][1];
		yz = m[1][2];

		zx = m[2][0];
		zy = m[2][1];
		zz = m[2][2];
	}

	/**
	 * Copy Constructor
	 */
	public Matrix3(Matrix3 m) {

		xx = m.xx;
		xy = m.xy;
		xz = m.xz;

		yx = m.yx;
		yy = m.yy;
		yz = m.yz;

		zx = m.zx;
		zy = m.zy;
		zz = m.zz;
	}

	/*
	 * ========================================================
	 * Set Matrix
	 * ========================================================
	 */

	/**
	 * 一次設定全部元素
	 */
	public Matrix3 set(double xx, double xy, double xz, double yx, double yy, double yz, double zx, double zy, double zz) {

		this.xx = xx;
		this.xy = xy;
		this.xz = xz;

		this.yx = yx;
		this.yy = yy;
		this.yz = yz;

		this.zx = zx;
		this.zy = zy;
		this.zz = zz;

		return this;
	}

	/**
	 * 複製另一個 Matrix3
	 */
	public Matrix3 set(Matrix3 m) {

		xx = m.xx;
		xy = m.xy;
		xz = m.xz;

		yx = m.yx;
		yy = m.yy;
		yz = m.yz;

		zx = m.zx;
		zy = m.zy;
		zz = m.zz;

		return this;
	}

	/**
	 * 全部設為 0
	 */
	public Matrix3 zero() {

		xx = xy = xz = 0.0;
		yx = yy = yz = 0.0;
		zx = zy = zz = 0.0;

		return this;
	}

	/**
	 * 設為單位矩陣
	 */
	public Matrix3 identity() {

		xx = 1.0;
		xy = 0.0;
		xz = 0.0;

		yx = 0.0;
		yy = 1.0;
		yz = 0.0;

		zx = 0.0;
		zy = 0.0;
		zz = 1.0;

		return this;
	}

	/**
	 * 建立單位矩陣
	 */
	public static Matrix3 Identity() {

		return new Matrix3().identity();
	}

	/**
	 * 建立零矩陣
	 */
	public static Matrix3 Zero() {

		return new Matrix3();
	}

	/*
	 * ========================================================
	 * 元素存取 (Element Access)
	 * ========================================================
	 */

	/**
	 * 取得矩陣元素
	 *
	 * @param row 列 (0~2)
	 * @param col 行 (0~2)
	 * @return 元素值
	 */
	public double get(int row, int col) {

		switch (row) {

		case 0:
			switch (col) {
			case 0:
				return xx;
			case 1:
				return xy;
			case 2:
				return xz;
			}
			break;

		case 1:
			switch (col) {
			case 0:
				return yx;
			case 1:
				return yy;
			case 2:
				return yz;
			}
			break;

		case 2:
			switch (col) {
			case 0:
				return zx;
			case 1:
				return zy;
			case 2:
				return zz;
			}
			break;
		}

		throw new IndexOutOfBoundsException("Matrix3 index (" + row + "," + col + ") out of range.");
	}

	/**
	 * 設定矩陣元素
	 *
	 * @param row 列
	 * @param col 行
	 * @param value 新值
	 */
	public void set(int row, int col, double value) {

		switch (row) {

		case 0:
			switch (col) {
			case 0:
				xx = value;
				return;
			case 1:
				xy = value;
				return;
			case 2:
				xz = value;
				return;
			}
			break;

		case 1:
			switch (col) {
			case 0:
				yx = value;
				return;
			case 1:
				yy = value;
				return;
			case 2:
				yz = value;
				return;
			}
			break;

		case 2:
			switch (col) {
			case 0:
				zx = value;
				return;
			case 1:
				zy = value;
				return;
			case 2:
				zz = value;
				return;
			}
			break;
		}

		throw new IndexOutOfBoundsException("Matrix3 index (" + row + "," + col + ") out of range.");
	}

	/*
	 * ========================================================
	 * Copy
	 * ========================================================
	 */

	/**
	 * 建立完整複本
	 */
	public Matrix3 copy() {

		return new Matrix3(this);
	}

	/**
	 * 從另一矩陣複製資料
	 */
	public Matrix3 copyFrom(Matrix3 m) {

		xx = m.xx;
		xy = m.xy;
		xz = m.xz;

		yx = m.yx;
		yy = m.yy;
		yz = m.yz;

		zx = m.zx;
		zy = m.zy;
		zz = m.zz;

		return this;
	}

	/*
	 * ========================================================
	 * Clear / Reset
	 * ========================================================
	 */

	/**
	 * 清除矩陣 (全部設為 0)
	 */
	public void clear() {

		xx = xy = xz = 0.0;
		yx = yy = yz = 0.0;
		zx = zy = zz = 0.0;
	}

	/**
	 * 是否為零矩陣
	 */
	public boolean isZero() {

		return xx == 0.0 && xy == 0.0 && xz == 0.0 &&

				yx == 0.0 && yy == 0.0 && yz == 0.0 &&

				zx == 0.0 && zy == 0.0 && zz == 0.0;
	}

	/*
	 * ========================================================
	 * Matrix Property
	 * ========================================================
	 */

	/**
	 * Trace
	 *
	 * tr(A)
	 */
	public double trace() {

		return xx + yy + zz;
	}

	/**
	 * 是否為單位矩陣
	 */
	public boolean isIdentity() {

		return xx == 1.0 && yy == 1.0 && zz == 1.0 &&

				xy == 0.0 && xz == 0.0 && yx == 0.0 && yz == 0.0 && zx == 0.0 && zy == 0.0;
	}

	/*
	 * ========================================================
	 * Array Convert
	 * ========================================================
	 */

	/**
	 * 轉成 double[3][3]
	 *
	 * (僅供 IO 或相容舊程式使用)
	 */
	public double[][] toArray() {

		return new double[][] {

				{ xx, xy, xz },

				{ yx, yy, yz },

				{ zx, zy, zz }

		};
	}

	/*
	 * ========================================================
	 * Matrix Invariants
	 *
	 * 矩陣不變量
	 * ========================================================
	 */

	/**
	 * Trace
	 *
	 * tr(A)=Axx+Ayy+Azz
	 *
	 * 常用於：
	 *
	 * 1. 體積應變
	 * 2. Stress invariant
	 * 3. Drucker-Prager
	 */
	public double traceValue() {

		return xx + yy + zz;
	}

	/**
	 * 第二不變量 I2
	 *
	 * I2 =
	 *
	 * 1/2[(trA)^2-tr(A^2)]
	 *
	 */
	public double secondInvariant() {

		double tr = traceValue();

		double a2 = xx * xx + xy * yx + xz * zx +

				yx * xy + yy * yy + yz * zy +

				zx * xz + zy * yz + zz * zz;

		return 0.5 * (tr * tr - a2);
	}

	/**
	 * Determinant
	 *
	 * |A|
	 *
	 * 用於：
	 *
	 * deformation gradient J
	 *
	 */
	public double determinant() {

		return

		xx * (yy * zz - yz * zy)

				- xy * (yx * zz - yz * zx)

				+ xz * (yx * zy - yy * zx);

	}

	/*
	 * ========================================================
	 * Norm
	 * ========================================================
	 */

	/**
	 * Frobenius Norm
	 *
	 * ||A|| =
	 * sqrt(sum(Aij^2))
	 */
	public double frobeniusNorm() {

		return Math.sqrt(

				xx * xx + xy * xy + xz * xz +

						yx * yx + yy * yy + yz * yz +

						zx * zx + zy * zy + zz * zz

		);

	}

	/**
	 * 最大絕對元素
	 */
	public double maxAbs() {

		double max = Math.abs(xx);

		max = Math.max(max, Math.abs(xy));
		max = Math.max(max, Math.abs(xz));

		max = Math.max(max, Math.abs(yx));
		max = Math.max(max, Math.abs(yy));
		max = Math.max(max, Math.abs(yz));

		max = Math.max(max, Math.abs(zx));
		max = Math.max(max, Math.abs(zy));
		max = Math.max(max, Math.abs(zz));

		return max;

	}

	/*
	 * ========================================================
	 * Symmetry Check
	 * ========================================================
	 */

	/**
	 * 判斷是否為對稱矩陣
	 *
	 * A=A^T
	 *
	 */
	public boolean isSymmetric(double tolerance) {

		return

		Math.abs(xy - yx) < tolerance

				&&

				Math.abs(xz - zx) < tolerance

				&&

				Math.abs(yz - zy) < tolerance;

	}

	/**
	 * 對稱誤差
	 *
	 * ||A-AT||
	 */
	public double symmetryError() {

		double a = xy - yx;

		double b = xz - zx;

		double c = yz - zy;

		return Math.sqrt(a * a + b * b + c * c);

	}

	/*
	 * ========================================================
	 * Convert
	 * ========================================================
	 */

	/**
	 * 複製到 double array
	 *
	 * 只供：
	 *
	 * 1. IO
	 * 2. Debug
	 * 3. 舊程式相容
	 *
	 */
	public void copyToArray(double[][] out) {

		out[0][0] = xx;
		out[0][1] = xy;
		out[0][2] = xz;

		out[1][0] = yx;
		out[1][1] = yy;
		out[1][2] = yz;

		out[2][0] = zx;
		out[2][1] = zy;
		out[2][2] = zz;

	}

	/**
	 * 建立 double array
	 */
	public double[][] array() {

		return new double[][] {

				{ xx, xy, xz },

				{ yx, yy, yz },

				{ zx, zy, zz }

		};

	}

	/*
	 * ========================================================
	 * Basic Matrix Operations
	 *
	 * A+B
	 * A-B
	 * ========================================================
	 */

	/**
	 * 矩陣加法
	 *
	 * C=A+B
	 *
	 */
	public Matrix3 add(Matrix3 b) {

		return new Matrix3(

				xx + b.xx, xy + b.xy, xz + b.xz,

				yx + b.yx, yy + b.yy, yz + b.yz,

				zx + b.zx, zy + b.zy, zz + b.zz

		);

	}

	/**
	 * 矩陣減法
	 *
	 * C=A-B
	 */
	public Matrix3 subtract(Matrix3 b) {

		return new Matrix3(

				xx - b.xx, xy - b.xy, xz - b.xz,

				yx - b.yx, yy - b.yy, yz - b.yz,

				zx - b.zx, zy - b.zy, zz - b.zz

		);

	}

	/*
	 * ========================================================
	 * In-place Operation
	 *
	 * MPM核心版本
	 * 不建立新物件
	 * ========================================================
	 */

	/**
	 * A += B
	 */
	public Matrix3 addInPlace(Matrix3 b) {

		xx += b.xx;
		xy += b.xy;
		xz += b.xz;

		yx += b.yx;
		yy += b.yy;
		yz += b.yz;

		zx += b.zx;
		zy += b.zy;
		zz += b.zz;

		return this;

	}

	/**
	 * A -= B
	 */
	public Matrix3 subtractInPlace(Matrix3 b) {

		xx -= b.xx;
		xy -= b.xy;
		xz -= b.xz;

		yx -= b.yx;
		yy -= b.yy;
		yz -= b.yz;

		zx -= b.zx;
		zy -= b.zy;
		zz -= b.zz;

		return this;

	}

	/**
	 * A *= scalar
	 */
	public Matrix3 scaleInPlace(double s) {

		xx *= s;
		xy *= s;
		xz *= s;

		yx *= s;
		yy *= s;
		yz *= s;

		zx *= s;
		zy *= s;
		zz *= s;

		return this;

	}

	/**
	 * 純量乘法
	 *
	 * B=cA
	 */
	public Matrix3 multiply(double s) {

		return new Matrix3(

				xx * s, xy * s, xz * s,

				yx * s, yy * s, yz * s,

				zx * s, zy * s, zz * s

		);

	}

	/*
	 * ========================================================
	 * Matrix Multiplication
	 *
	 * C=A*B
	 * ========================================================
	 */

	/**
	 * 矩陣乘法
	 *
	 * C=A*B
	 */
	public Matrix3 multiply(Matrix3 b) {

		return new Matrix3(

				xx * b.xx + xy * b.yx + xz * b.zx,

				xx * b.xy + xy * b.yy + xz * b.zy,

				xx * b.xz + xy * b.yz + xz * b.zz,

				yx * b.xx + yy * b.yx + yz * b.zx,

				yx * b.xy + yy * b.yy + yz * b.zy,

				yx * b.xz + yy * b.yz + yz * b.zz,

				zx * b.xx + zy * b.yx + zz * b.zx,

				zx * b.xy + zy * b.yy + zz * b.zy,

				zx * b.xz + zy * b.yz + zz * b.zz

		);

	}

	/**
	 * 矩陣乘Vector
	 *
	 * y=A*x
	 */
	public Vector3 multiply(Vector3 v) {

		return new Vector3(

				xx * v.x + xy * v.y + xz * v.z,

				yx * v.x + yy * v.y + yz * v.z,

				zx * v.x + zy * v.y + zz * v.z

		);

	}

	/*
	 * ========================================================
	 * Transpose
	 *
	 * A^T
	 * ========================================================
	 */

	/**
	 * 轉置矩陣
	 */
	public Matrix3 transpose() {

		return new Matrix3(

				xx, yx, zx,

				xy, yy, zy,

				xz, yz, zz

		);

	}

	/**
	 * ========================================================
	 * Matrix inverse
	 *
	 * A^-1
	 *
	 * 使用：
	 *
	 * A^-1 = adj(A)/det(A)
	 *
	 * 適用：
	 *
	 * 1. Deformation Gradient F
	 * 2. Velocity Gradient
	 * 3. Coordinate transformation
	 *
	 * ========================================================
	 */
	public Matrix3 inverse() {

		double det = determinant();

		if (Math.abs(det) < 1.0e-14) {
			throw new RuntimeException("Matrix3 inverse failed: determinant = " + det);
		}

		double invDet = 1.0 / det;

		return new Matrix3(

				/*
				 * Row 0
				 */

				(yy * zz - yz * zy) * invDet,

				(xz * zy - xy * zz) * invDet,

				(xy * yz - xz * yy) * invDet,

				/*
				 * Row 1
				 */

				(yz * zx - yx * zz) * invDet,

				(xx * zz - xz * zx) * invDet,

				(xz * yx - xx * yz) * invDet,

				/*
				 * Row 2
				 */

				(yx * zy - yy * zx) * invDet,

				(xy * zx - xx * zy) * invDet,

				(xx * yy - xy * yx) * invDet

		);

	}
	
	/**
	 * ========================================================
	 * Add Outer Product
	 *
	 * A += a ⊗ b
	 *
	 * 外積：
	 *
	 *        | ax*bx ax*by ax*bz |
	 * a⊗b =  | ay*bx ay*by ay*bz |
	 *        | az*bx az*by az*bz |
	 *
	 *
	 * MPM用途：
	 *
	 * Velocity Gradient
	 * Stress Update
	 * P2G Transfer
	 *
	 *
	 * In-place operation
	 *
	 * 不建立新物件
	 *
	 * ========================================================
	 */
	public Matrix3 addOuterProduct(
	        Vector3 a,
	        Vector3 b) {

	    xx += a.x * b.x;
	    xy += a.x * b.y;
	    xz += a.x * b.z;

	    yx += a.y * b.x;
	    yy += a.y * b.y;
	    yz += a.y * b.z;

	    zx += a.z * b.x;
	    zy += a.z * b.y;
	    zz += a.z * b.z;

	    return this;

	}	

}