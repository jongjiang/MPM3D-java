package tw.edu.mpm.math;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * Tensor3.java
 *
 * 三維二階張量
 *
 *
 * 使用 9 個 double 儲存
 *
 *
 *        | xx xy xz |
 *  T  =  | yx yy yz |
 *        | zx zy zz |
 *
 *
 * 用於：
 *
 * Stress       σ
 * Strain       ε
 * StrainRate   D
 * Deformation Tensor
 *
 *
 * ============================================================
 */
public class Tensor3 {

	/*
	 * ========================================================
	 * Tensor components
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
	 * Constructor
	 * ========================================================
	 */

	/**
	 * 建立零張量
	 */
	public Tensor3() {

	}

	/**
	 * 建立指定張量
	 */
	public Tensor3(
			double xx, double xy, double xz,
			double yx, double yy, double yz,
			double zx, double zy, double zz) {

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
	 * Copy Constructor
	 */
	public Tensor3(Tensor3 t) {

		xx = t.xx;
		xy = t.xy;
		xz = t.xz;

		yx = t.yx;
		yy = t.yy;
		yz = t.yz;

		zx = t.zx;
		zy = t.zy;
		zz = t.zz;

	}

	/*
	 * ========================================================
	 * Set
	 * ========================================================
	 */

	/**
	 * 一次設定全部元素
	 */
	public Tensor3 set(

			double xx, double xy, double xz,

			double yx, double yy, double yz,

			double zx, double zy, double zz) {

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
	 * 複製另一Tensor
	 */
	public Tensor3 set(Tensor3 t) {

		xx = t.xx;
		xy = t.xy;
		xz = t.xz;

		yx = t.yx;
		yy = t.yy;
		yz = t.yz;

		zx = t.zx;
		zy = t.zy;
		zz = t.zz;

		return this;

	}

	/**
	 * 建立複本
	 */
	public Tensor3 copy() {

		return new Tensor3(this);

	}

	/*
	 * ========================================================
	 * Initialize
	 * ========================================================
	 */

	/**
	 * 全部歸零
	 */
	public Tensor3 zero() {

		xx = 0.0;
		xy = 0.0;
		xz = 0.0;

		yx = 0.0;
		yy = 0.0;
		yz = 0.0;

		zx = 0.0;
		zy = 0.0;
		zz = 0.0;

		return this;

	}

	/**
	 * 單位張量
	 *
	 * I
	 */
	public Tensor3 identity() {

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
	 * 建立單位張量
	 */
	public static Tensor3 identityTensor() {

		return new Tensor3().identity();

	}

	/**
	 * 建立零張量
	 */
	public static Tensor3 zeroTensor() {

		return new Tensor3();

	}

	/*
	 * ========================================================
	 * Element Access
	 *
	 * get / set
	 * ========================================================
	 */

	/**
	 * 取得元素
	 *
	 * row : 0~2
	 * col : 0~2
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

		throw new IndexOutOfBoundsException("Tensor3 index out of range");
	}

	/**
	 * 設定元素
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

		throw new IndexOutOfBoundsException("Tensor3 index out of range");

	}

	/*
	 * ========================================================
	 * Basic Tensor Operations
	 *
	 * A+B
	 * A-B
	 * ========================================================
	 */

	/**
	 * Tensor addition
	 *
	 * C=A+B
	 */
	public Tensor3 add(Tensor3 b) {

		return new Tensor3(

				xx + b.xx, xy + b.xy, xz + b.xz,

				yx + b.yx, yy + b.yy, yz + b.yz,

				zx + b.zx, zy + b.zy, zz + b.zz

		);

	}

	/**
	 * Tensor subtraction
	 *
	 * C=A-B
	 */
	public Tensor3 subtract(Tensor3 b) {

		return new Tensor3(

				xx - b.xx, xy - b.xy, xz - b.xz,

				yx - b.yx, yy - b.yy, yz - b.yz,

				zx - b.zx, zy - b.zy, zz - b.zz

		);

	}

	/**
	 * Scalar multiplication
	 *
	 * C=aT
	 */
	public Tensor3 multiply(double s) {

		return new Tensor3(

				xx * s, xy * s, xz * s,

				yx * s, yy * s, yz * s,

				zx * s, zy * s, zz * s

		);

	}

	/*
	 * ========================================================
	 * In-place Operations
	 *
	 * MPM 高效版本
	 * ========================================================
	 */

	/**
	 * A += B
	 */
	public Tensor3 addInPlace(Tensor3 b) {

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
	public Tensor3 subtractInPlace(Tensor3 b) {

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
	 * T *= scalar
	 *
	 * In-place
	 */
	public Tensor3 scaleInPlace(double s) {

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

	/*
	 * ========================================================
	 * Tensor Invariants
	 *
	 * ========================================================
	 */

	/**
	 * Trace
	 *
	 * tr(T)=Txx+Tyy+Tzz
	 *
	 */
	public double trace() {

		return xx + yy + zz;

	}

	/**
	 * 轉置
	 *
	 * T^T
	 *
	 */
	public Tensor3 transpose() {

		return new Tensor3(

				xx, yx, zx,

				xy, yy, zy,

				xz, yz, zz

		);

	}

	/**
	 * 對稱部分
	 *
	 * sym(T)=1/2(T+T^T)
	 *
	 *
	 * Stress / Strain 常用
	 *
	 */
	public Tensor3 symmetric() {

		double half = 0.5;

		return new Tensor3(

				xx,

				(xy + yx) * half,

				(xz + zx) * half,

				(yx + xy) * half,

				yy,

				(yz + zy) * half,

				(zx + xz) * half,

				(zy + yz) * half,

				zz

		);

	}

	/**
	 * Deviatoric Tensor
	 *
	 *
	 * S = T - 1/3 tr(T) I
	 *
	 *
	 * 用於：
	 *
	 * von Mises
	 * Plasticity
	 *
	 */
	public Tensor3 deviatoric() {

		double mean = trace() / 3.0;

		return new Tensor3(

				xx - mean, xy, xz,

				yx, yy - mean, yz,

				zx, zy, zz - mean

		);

	}

	/*
	 * ========================================================
	 * Tensor Inner Product
	 *
	 * A:B
	 *
	 * ========================================================
	 */

	/**
	 * Double contraction
	 *
	 * A:B =
	 *
	 * sum(Aij Bij)
	 *
	 */
	public double doubleDot(Tensor3 b) {

		return

		xx * b.xx + xy * b.xy + xz * b.xz +

				yx * b.yx + yy * b.yy + yz * b.yz +

				zx * b.zx + zy * b.zy + zz * b.zz;

	}

	/**
	 * Tensor norm
	 *
	 * ||T|| = sqrt(T:T)
	 *
	 */
	public double norm() {

		return Math.sqrt(doubleDot(this));

	}

	/**
	 * 第二不變量 J2
	 *
	 * J2 = 1/2 S:S
	 *
	 *
	 * 用於：
	 *
	 * Mohr-Coulomb
	 * Drucker-Prager
	 *
	 */
	public double secondInvariant() {

		Tensor3 s = deviatoric();

		return 0.5 * s.doubleDot(s);

	}

	/**
	 * von Mises stress
	 *
	 *
	 * sigma_vm =
	 *
	 * sqrt(3/2 S:S)
	 *
	 */
	public double vonMises() {

		Tensor3 s = deviatoric();

		return Math.sqrt(1.5 * s.doubleDot(s));

	}

	/*
	 * ========================================================
	 * Add diagonal
	 *
	 * T += aI
	 *
	 * ========================================================
	 */

	/**
	 * 對角線增加
	 *
	 * 常用於：
	 *
	 * stress += pressure I
	 *
	 */
	public Tensor3 addDiagonal(double value) {

		xx += value;

		yy += value;

		zz += value;

		return this;

	}

	/*
	 * ========================================================
	 * Outer Product
	 *
	 * A = a ⊗ b
	 *
	 * ========================================================
	 */

	/**
	 * 建立外積張量
	 *
	 * Aij = ai bj
	 *
	 */
	public static Tensor3 outerProduct(Vector3 a, Vector3 b) {

		return new Tensor3(

				a.x * b.x, a.x * b.y, a.x * b.z,

				a.y * b.x, a.y * b.y, a.y * b.z,

				a.z * b.x, a.z * b.y, a.z * b.z

		);

	}

	/**
	 * 累加外積
	 *
	 * T += a⊗b
	 *
	 * MPM:
	 *
	 * velocity gradient
	 *
	 */
	public Tensor3 addOuterProduct(Vector3 a, Vector3 b) {

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

	/**
	 * 加權外積
	 *
	 * T += weight(a⊗b)
	 *
	 */
	public Tensor3 addOuterProduct(Vector3 a, Vector3 b, double weight) {

		double w = weight;

		xx += w * a.x * b.x;
		xy += w * a.x * b.y;
		xz += w * a.x * b.z;

		yx += w * a.y * b.x;
		yy += w * a.y * b.y;
		yz += w * a.y * b.z;

		zx += w * a.z * b.x;
		zy += w * a.z * b.y;
		zz += w * a.z * b.z;

		return this;

	}

	/*
	 * ========================================================
	 * Matrix3 conversion
	 *
	 * ========================================================
	 */

	/**
	 * Tensor3 -> Matrix3
	 */
	public Matrix3 toMatrix3() {

		return new Matrix3(

				xx, xy, xz,

				yx, yy, yz,

				zx, zy, zz

		);

	}

	/**
	 * Matrix3 -> Tensor3
	 */
	public static Tensor3 fromMatrix(Matrix3 m) {

		return new Tensor3(

				m.xx, m.xy, m.xz,

				m.yx, m.yy, m.yz,

				m.zx, m.zy, m.zz

		);

	}

	/*
	 * ========================================================
	 * Validation
	 * ========================================================
	 */

	/**
	 * 判斷是否包含 NaN / Infinity
	 */
	public boolean isFinite() {

		return

		Double.isFinite(xx) && Double.isFinite(xy) && Double.isFinite(xz)

				&&

				Double.isFinite(yx) && Double.isFinite(yy) && Double.isFinite(yz)

				&&

				Double.isFinite(zx) && Double.isFinite(zy) && Double.isFinite(zz);

	}

	/*
	 * ========================================================
	 * Convert
	 * ========================================================
	 */

	/**
	 * 轉換成 double[3][3]
	 *
	 * 僅供：
	 *
	 * Debug
	 * IO
	 *
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
	 * Output
	 * ========================================================
	 */

	@Override
	public String toString() {

		return String.format(

				"| %.6e %.6e %.6e |\n" + "| %.6e %.6e %.6e |\n" + "| %.6e %.6e %.6e |",

				xx, xy, xz,

				yx, yy, yz,

				zx, zy, zz

		);

	}

}